package org.parafia.webapp.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.parafia.webapp.util.MyHttpServletRequestWrapper;

public class DefaultSortingFilter implements Filter {
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws ServletException, IOException {
		
		HttpServletRequest request = (HttpServletRequest) req;
		req = new MyHttpServletRequestWrapper(request);

		chain.doFilter(req, res);
	}

	public void destroy() {
		
	}

	public void init(FilterConfig arg0) throws ServletException {
		
	}
}
