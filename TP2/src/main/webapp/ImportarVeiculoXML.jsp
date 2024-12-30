<%@ page import="java.io.*, java.util.ArrayList, java.util.List"%>
<%@ page import="java.sql.Timestamp, java.time.LocalDateTime"%>
<%@ page
	import="javax.xml.parsers.DocumentBuilder, javax.xml.parsers.DocumentBuilderFactory"%>
<%@ page
	import="org.w3c.dom.Document, org.w3c.dom.Element, org.w3c.dom.Node, org.w3c.dom.NodeList"%>
<%@ page import="jakarta.servlet.http.Part"%>
<%@ page import="db.VeiculoDao, db.IntervencaoDao"%>
<%@ page import="pojo.Veiculo, pojo.Intervencao"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Importar Veículo XML</title>
<style>
body {
	font-family: Arial, sans-serif;
	margin: 20px;
}

.button {
	padding: 10px 20px;
	background-color: #4CAF50;
	color: white;
	border: none;
	text-decoration: none;
	border-radius: 5px;
}

.button:hover {
	background-color: #45a049;
}
</style>
</head>
<body>
	<h1>Importar Veículo XML</h1>
	<form method="post" enctype="multipart/form-data">
		<label>Selecionar Arquivo XML: <input type="file"
			name="xmlFile" accept=".xml" required>
		</label>
		<button type="submit" class="button">Importar</button>
	</form>

	<%
	if ("POST".equalsIgnoreCase(request.getMethod())) {
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

		// Configurar parser XML
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(new ByteArrayInputStream(xml.getBytes()));

		document.getDocumentElement().normalize();

		// Obter os dados do veículo
		Element veiculoElement = (Element) document.getElementsByTagName("veiculo").item(0);

		Veiculo veiculo = new Veiculo();
		veiculo.setMatricula(veiculoElement.getElementsByTagName("matricula").item(0).getTextContent());
		veiculo.setNomeMarca(veiculoElement.getElementsByTagName("marca").item(0).getTextContent());
		veiculo.setNomeMod(veiculoElement.getElementsByTagName("modelo").item(0).getTextContent());
		veiculo.setCor(veiculoElement.getElementsByTagName("cor").item(0).getTextContent());
		veiculo.setPotencia(
				Integer.parseInt(veiculoElement.getElementsByTagName("potencia").item(0).getTextContent()));
		veiculo.setCombustivel(veiculoElement.getElementsByTagName("combustivel").item(0).getTextContent());

		// Obter as intervenções
		NodeList intervencaoNodes = veiculoElement.getElementsByTagName("intervencao");
		List<Intervencao> intervencoes = new ArrayList<>();

		for (int i = 0; i < intervencaoNodes.getLength(); i++) {
			Node node = intervencaoNodes.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element intervencaoElement = (Element) node;

				Intervencao intervencao = new Intervencao();
				intervencao.setNumKM(Integer
						.parseInt(intervencaoElement.getElementsByTagName("numKm").item(0).getTextContent()));
				intervencao.setDhRegisto(
						Timestamp.valueOf(intervencaoElement.getElementsByTagName("data").item(0).getTextContent())
								.toLocalDateTime());
				intervencao.setTipoInt(intervencaoElement.getElementsByTagName("tipo").item(0).getTextContent());
				intervencao.setCustoInt(Double
						.parseDouble(intervencaoElement.getElementsByTagName("custo").item(0).getTextContent()));
				intervencao.setMatricula(veiculo.getMatricula());

				intervencoes.add(intervencao);
			}
		}

		// Salvar os dados no banco de dados
		VeiculoDao veiculoDao = new VeiculoDao();
		IntervencaoDao intervencaoDao = new IntervencaoDao();

		veiculoDao.save(veiculo);
		for (Intervencao intervencao : intervencoes) {
			intervencaoDao.save(intervencao);
		}

		out.println("<p style='color: green;'>Veículo e intervenções importados com sucesso!</p>");
			}
		} catch (Exception e) {
			e.printStackTrace();
			out.println("<p style='color: red;'>Erro ao importar o XML. Verifique o formato do arquivo.</p>");
		}
	}
	%>
</body>
</html>
