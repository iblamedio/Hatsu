using Application.UseCases.PlayerUseCases.LoginUseCase;
using Application.UseCases.PlayerUseCases.RegisterPlayerUseCase;
using Microsoft.AspNetCore.Mvc;

namespace Api.Controllers;

[ApiController]
[Route("api/[controller]")]
public class AuthController(RegisterPlayerUseCase registerUseCase, LoginUseCase loginUseCase) : ControllerBase
{
    [HttpPost("register")]
    public async Task<IActionResult> Register(RegisterPlayerUseCaseInput input)
    {
        return Ok(await registerUseCase.ExecuteAsync(input));
    }
    
    [HttpPost("login")]
    public async Task<IActionResult> Login(LoginUseCaseInput input)
    {
        return Ok(await loginUseCase.ExecuteAsync(input));
    }
}