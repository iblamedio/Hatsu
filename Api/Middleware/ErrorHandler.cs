using Microsoft.AspNetCore.Mvc;

namespace Api.Middleware;

public class ErrorHandler(ILogger<ErrorHandler> logger, RequestDelegate next)
{
    public async Task Invoke(HttpContext context)
    {
        try
        {
            await next(context);
        }
        catch (InvalidOperationException ex)
        {
            const int code = StatusCodes.Status500InternalServerError;
            
            var problem = new ProblemDetails
            {
                Title = "Error processing request",
                Status = code,
                Detail = ex.Message
            };

            context.Response.StatusCode = code;
            await context.Response.WriteAsJsonAsync(problem);
        }
        catch (Exception ex)
        {
            logger.LogError(ex, ex.Message);
            
            const int code = StatusCodes.Status500InternalServerError;
            
            var problem = new ProblemDetails
            {
                Title = "Internal error",
                Status = code,
                Detail = ex.Message
            };

            context.Response.StatusCode = code;
            await context.Response.WriteAsJsonAsync(problem);
        }
    }
}