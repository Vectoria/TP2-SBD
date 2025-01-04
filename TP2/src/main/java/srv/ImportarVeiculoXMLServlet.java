package srv;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import pojo.Intervencao;
import pojo.Veiculo;

import org.w3c.dom.*;

import db.IntervencaoDao;
import db.VeiculoDao;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/ImportarVeiculoXMLServlet")
@MultipartConfig
public class ImportarVeiculoXMLServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			Part filePart = request.getPart("xmlFile");
			if (filePart != null) {
				InputStream fileContent = filePart.getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(fileContent));
				StringBuilder xmlContent = new StringBuilder();
				String line;

				while ((line = reader.readLine()) != null) {
					xmlContent.append(line);
				}

				String xml = xmlContent.toString();

				// Parse XML
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document document = builder.parse(new ByteArrayInputStream(xml.getBytes()));
				document.getDocumentElement().normalize();

				// Extrai a tag veiculo e o atribui os valores
				Element veiculoElement = (Element) document.getElementsByTagName("veiculo").item(0);

				Veiculo veiculo = new Veiculo();
				veiculo.setMatricula(veiculoElement.getElementsByTagName("matricula").item(0).getTextContent());
				veiculo.setNomeMarca(veiculoElement.getElementsByTagName("marca").item(0).getTextContent());
				veiculo.setNomeMod(veiculoElement.getElementsByTagName("modelo").item(0).getTextContent());
				veiculo.setCor(veiculoElement.getElementsByTagName("cor").item(0).getTextContent());
				veiculo.setPotencia(
						Integer.parseInt(veiculoElement.getElementsByTagName("potencia").item(0).getTextContent()));
				veiculo.setCombustivel(veiculoElement.getElementsByTagName("combustivel").item(0).getTextContent());

				// extrai as tags intervencao
				NodeList intervencaoNodes = veiculoElement.getElementsByTagName("intervencao");
				List<Intervencao> intervencoes = new ArrayList<>();

				for (int i = 0; i < intervencaoNodes.getLength(); i++) {
					Node node = intervencaoNodes.item(i);
					if (node.getNodeType() == Node.ELEMENT_NODE) {
						Element intervencaoElement = (Element) node;

						Intervencao intervencao = new Intervencao();
						intervencao.setNumKM(Integer
								.parseInt(intervencaoElement.getElementsByTagName("numKm").item(0).getTextContent()));
						String dhRegistoStr = intervencaoElement.getElementsByTagName("dhRegisto").item(0)
								.getTextContent();
						dhRegistoStr = dhRegistoStr.replace('T', ' ') + ":00";
						intervencao.setDhRegisto(Timestamp.valueOf(dhRegistoStr).toLocalDateTime());
						intervencao.setTipoInt(
								intervencaoElement.getElementsByTagName("tipoInt").item(0).getTextContent());
						intervencao.setCustoInt(Double.parseDouble(
								intervencaoElement.getElementsByTagName("custoInt").item(0).getTextContent()));
						intervencao.setMatricula(veiculo.getMatricula());

						intervencoes.add(intervencao);
					}
				}

				// carrega-se a base de dados
				VeiculoDao veiculoDao = new VeiculoDao();
				IntervencaoDao intervencaoDao = new IntervencaoDao();

				veiculoDao.save(veiculo);
				for (Intervencao intervencao : intervencoes) {
					intervencaoDao.save(intervencao);
				}

				response.sendRedirect("perfil0.jsp");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter()
					.println("<p style='color: red;'>Erro ao importar o XML. Verifique o formato do arquivo.</p>");
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect("/perfil0.jsp");
	}
}