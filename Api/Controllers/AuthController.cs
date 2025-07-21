using Application.UseCases.PlayerUseCases.LoginUseCase;
using Application.UseCases.PlayerUseCases.RegisterPlayerUseCase;
using Microsoft.AspNetCore.Mvc;

namespace Api.Controllers;

[ApiController]
[Route("api/[controller]")]
public class AuthController(RegisterPlayerUseCase registerUseCase, LoginUseCase loginUseCase) : ControllerBase
{
    [HttpPost("register")]
    public async Task<IActionResult> Register([FromBody] RegisterPlayerUseCaseInput input)
    {
        return Ok(await registerUseCase.ExecuteAsync(input));
    }
    
    [HttpPost("login")]
    public async Task<IActionResult> Login([FromBody] LoginUseCaseInput input)
    {
        return Ok(await loginUseCase.ExecuteAsync(input));
    }
}