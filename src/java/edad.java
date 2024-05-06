import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet para calcular la edad basada en una fecha de nacimiento proporcionada como parámetro.
 */
public class edad extends HttpServlet {  // Mantén el nombre en minúsculas para coincidir con el nombre del archivo

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Edad calculada</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Resultado del cálculo de edad</h1>");  

      
            out.println("<p>Context path: " + request.getContextPath() + "</p>");  
            out.println("<p>Información del servlet: " + this.getServletInfo() + "</p>");  
            
          
            if (request.getAttribute("edad") != null) {
                out.println("<p>Edad calculada: " + request.getAttribute("edad") + " años</p>");
            } else {
                out.println("<p>No se pudo calcular la edad.</p>"); 
            }

            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String strFecha = request.getParameter("fecha");  
        Date fechaNac = null;  

        if (strFecha != null) { 
            try {
                fechaNac = new SimpleDateFormat("dd/MM/yyyy").parse(strFecha); 
            } catch (ParseException ex) {
                Logger.getLogger(edad.class.getName()).log(Level.SEVERE, "Error parsing date", ex);
            }
        }

        if (fechaNac != null) {  
            Calendar fechaNacimiento = Calendar.getInstance();
            Calendar fechaActual = Calendar.getInstance();
            fechaNacimiento.setTime(fechaNac);
            int edad = fechaActual.get(Calendar.YEAR) - fechaNacimiento.get(Calendar.YEAR);

            if (fechaActual.get(Calendar.DAY_OF_YEAR) < fechaNacimiento.get(Calendar.DAY_OF_YEAR)) {
                edad--; 
            }

            request.setAttribute("edad", edad);  
        }
        
        processRequest(request, response); 
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet para calcular la edad basada en una fecha de nacimiento proporcionada como parámetro.";
    }
}
