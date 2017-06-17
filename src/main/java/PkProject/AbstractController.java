package PkProject;

import PkProject.Model.ViewManager;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jtwig.web.servlet.JtwigRenderer;

/**
 *
 * @author daniel
 */
abstract public class AbstractController extends HttpServlet implements ControllerInterface
{
    protected ViewManager viewManager;
    
    protected HttpServletRequest request;
    
    protected HttpServletResponse response;
    
    protected final JtwigRenderer renderer = JtwigRenderer.defaultRenderer();
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.request = request;
        this.response = response;
        doGET();
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.request = request;
        this.response = response;
        doPOST();
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
    protected void render(String file, Map<String, String> parameters) {
        
        try {
            if (parameters != null) {
                for (String key : parameters.keySet()) {
                    request.setAttribute(key, parameters.get(key));
                }
            }
            
            file = file.replaceAll(":", "/");
            String filePath = "WEB-INF/"+file+".html.twig";
            
            renderer.dispatcherFor(filePath)
                    .render(request, response);
            
            
        } catch (ServletException ex) {
            Logger.getLogger(AbstractController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AbstractController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    protected void render(String file) {
        this.render(file, new HashMap<String, String>());
    }
}