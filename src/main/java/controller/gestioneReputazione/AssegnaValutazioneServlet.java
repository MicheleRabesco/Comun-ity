package controller.gestioneReputazione;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Utente;

/**
 * Servlet implementation class AssegnaValutazioneServlet.
 */
@WebServlet("/AssegnaValutazioneServlet")
public class AssegnaValutazioneServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /**
   * Default constructor.
   *
   *@see HttpServlet#HttpServlet()
   */
  public AssegnaValutazioneServlet() {
    super();
  }

  GestioneReputazioneService service = new GestioneReputazioneServiceImpl();

  /**
   * doGet method implementation.
   *
   * @throws IOException //
   * @throws ServletException //
   *@see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    HttpSession session = request.getSession(true);
    Utente utente = (Utente) session.getAttribute("user");

    if (utente == null) {
      response.sendRedirect("/Comun-ity/guest/login.jsp");
    } else {
      response.sendRedirect(" "); //jsp per assegnare la valutazione (da fare)
    }

  }

  /**
   * doPost method implementation.
   *
   * @throws IOException //
   * @throws ServletException //
   *@see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    doGet(request, response);

    HttpSession session = request.getSession(true);
    Utente utente = (Utente) session.getAttribute("user");
    Double valutazione = Double.valueOf(request.getParameter(" ")); //Inserire nome parametro

    service.assignRating(utente, valutazione);
    response.sendRedirect("HomeServlet");

  }

}
