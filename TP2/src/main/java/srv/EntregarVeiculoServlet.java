package srv;

import db.AluguerDao;
import db.LugarVeiculoDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/EntregarVeiculoServlet")
public class EntregarVeiculoServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String matricula = request.getParameter("matricula");
            String localidade = request.getParameter("localidade");
            int piso = Integer.parseInt(request.getParameter("piso"));
            String fila = request.getParameter("fila");
            int posFila = Integer.parseInt(request.getParameter("posFila"));
            int condutorNIF = Integer.parseInt(request.getParameter("condutorNIF"));


            LugarVeiculoDao lugarVeiculoDao = new LugarVeiculoDao();
            AluguerDao aluguerDao = new AluguerDao();
            LocalDateTime now = LocalDateTime.now();


            boolean lugarAtualizado = lugarVeiculoDao.inserirVeiculoNoLugar(matricula, localidade, piso, fila, posFila);
            boolean dataEntregaAtualizada = aluguerDao.atualizarDataEntrega(matricula, condutorNIF, now);

            if (lugarAtualizado && dataEntregaAtualizada) {
                // Redireciona para fazer outro forms
                response.sendRedirect("QualidadeServico_form.jsp");
            } else {
                response.getWriter().println("<h1>Erro ao atualizar o registro no banco de dados.</h1>");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("<h1>Erro ao processar a solicitação: " + e.getMessage() + "</h1>");
        }
    }
}
