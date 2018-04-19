package filter;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.POBean;

/**
 * Servlet Filter implementation class Anonymizer
 */
@WebFilter(description = "This filter is used to anonymize reports that we query", urlPatterns = { "/Anonymizer" })
public class Anonymizer implements Filter {

    /**
     * Default constructor. 
     */
    public Anonymizer() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		try {
			@SuppressWarnings("unchecked")
			List<POBean> list = (List<POBean>) req.getAttribute("anonymizedpo");
			Iterator<POBean> listErator = list.iterator();
			if (list != null) {
				while(listErator.hasNext()) {
					listErator.next().setUsername("******");
					listErator.next().setLname("Lname");
					listErator.next().setFname("Fname");
									}
				req.getSession().setAttribute("anonymizedpo", list);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}