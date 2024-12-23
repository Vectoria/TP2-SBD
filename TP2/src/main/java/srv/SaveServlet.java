package srv;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;

import db.CartaConducaoDao;
import db.CondutorDao;
import db.Dao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pojo.CartaConducao;
import pojo.Condutor;
import pojo.Pojo;

@WebServlet("/SaveServlet")
public class SaveServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        try {
            CartaConducao carta = new CartaConducao();
            Condutor condutor = new Condutor();
            
            // Parse and set values with null checks
            String numIDStr = request.getParameter("numID");
            if (numIDStr != null && !numIDStr.isEmpty()) {
                carta.setNumID(Integer.parseInt(numIDStr));
                condutor.setNumID(Integer.parseInt(numIDStr));
            }
            
            carta.setTipoHab(request.getParameter("tipoHab"));
            
            String dataEmissao = request.getParameter("dataEmissao");
            if (dataEmissao != null && !dataEmissao.isEmpty()) {
                carta.setDataEmissao(LocalDate.parse(dataEmissao));
            }
            
            String dataValidade = request.getParameter("dataValidade");
            if (dataValidade != null && !dataValidade.isEmpty()) {
                carta.setDataValidade(LocalDate.parse(dataValidade));
            }
            
            // Save carta first
            CartaConducaoDao cartaDao = new CartaConducaoDao();
            int cartaResult = cartaDao.save(carta);
            
            // If carta saved successfully, save condutor
            if (cartaResult > 0) {
                String dataNascimento = request.getParameter("dataNascimento");
                if (dataNascimento != null && !dataNascimento.isEmpty()) {
                    condutor.setDataNascimento(LocalDate.parse(dataNascimento));
                }
                
                String nifStr = request.getParameter("condutorNIF");
                if (nifStr != null && !nifStr.isEmpty()) {
                    condutor.setCondutorNIF(Integer.parseInt(nifStr));
                }
                
                CondutorDao condutorDao = new CondutorDao();
                int condutorResult = condutorDao.save(condutor);
                
                if (condutorResult > 0) {
                    out.print("<script>alert('Records saved successfully!');</script>");
                }
            }
            
        } catch (Exception e) {
            out.println("<h2>Error saving records: " + e.getMessage() + "</h2>");
            e.printStackTrace();
        }
        
        request.getRequestDispatcher("form.jsp").include(request, response);
    }
}
