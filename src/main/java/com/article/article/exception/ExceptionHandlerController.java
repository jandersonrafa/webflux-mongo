package com.article.article.exception;

/**
 *
 * @author Janderson
 */
//@ControllerAdvice
public class ExceptionHandlerController {
//public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

//	@ExceptionHandler(value = {AbstractBusinessException.class})
//	public RedirectView handleException(AbstractBusinessException ex, HttpServletRequest request) throws IOException {
//		RedirectView rw = new RedirectView(ex.getView());
//		FlashMap outputFlashMap = RequestContextUtils.getOutputFlashMap(request);
//		if (outputFlashMap != null) {
//			outputFlashMap.put("error", ex.getMessage());
//		}
//		return rw;
//	}
//
//	@ExceptionHandler(value = {ConstraintViolationException.class})
//	public RedirectView handleException(ConstraintViolationException ex, HttpServletRequest request) throws IOException {
//		RedirectView rw = new RedirectView(ex.getConstraintViolations().stream().findFirst().get().getMessage());
//		FlashMap outputFlashMap = RequestContextUtils.getOutputFlashMap(request);
//		if (outputFlashMap != null) {
//			outputFlashMap.put("error", ex.getMessage());
//		}
//		return rw;
//	}
//
//	return handleExceptionInternal(ex, ex.getMessage
//
//
//
//
//
//
//
//
//
//
//
//(), new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
//}
//	@ExceptionHandler(value = {UnauthorizedException.class})
//	protected ResponseEntity<Object> handleUnauthorized(UnauthorizedException ex, WebRequest request) {
//		return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
//	}
// @RequestHandler methods
// Exception handling methods
// Convert a predefined exception to an HTTP Status code
//@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Data integrity violation")  // 409
//		@ExceptionHandler(UnauthorizedException.class)
//		public String unauthorized() {
//		return
//		// Nothing to do
//	}
//	// Specify name of a specific view that will be used to display the error:
//	@ExceptionHandlerController({SQLException.class, DataAccessException.class})
//	public String databaseError() {
//		// Nothing to do.  Returns the logical view name of an error page, passed
//		// to the view-resolver(s) in usual way.
//		// Note that the exception is NOT available to this view (it is not added
//		// to the model) but see "Extending ExceptionHandlerExceptionResolver"
//		// below.
//		return "databaseError";
//	}
// Total control - setup a model and return the view name yourself. Or
// consider subclassing ExceptionHandlerExceptionResolver (see below).
//	@ExceptionHandlerController(Exception.class)
//	public ModelAndView handleError(HttpServletRequest req, Exception ex) {
//		logger.error("Request: " + req.getRequestURL() + " raised " + ex);
//
//		ModelAndView mav = new ModelAndView();
//		mav.addObject("exception", ex);
//		mav.addObject("url", req.getRequestURL());
//		mav.setViewName("error");
//		return mav;
//	}
}
