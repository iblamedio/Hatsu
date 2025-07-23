using System.Security.Claims;
using Application.UseCases.PlayerUseCases.FetchPlayerDataUseCase;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

namespace Api.Controllers;

[Authorize]
[ApiController]
[Route("api/[controller]")]
public class PlayerController(FetchPlayerDataUseCase fetchPlayerDataUseCase) : ControllerBase
{
    [HttpGet]
    public async Task<IActionResult> Get([FromQuery] string? id)
    {
        var playerId = id ?? User.FindFirst(ClaimTypes.NameIdentifier)?.Value;
        if (!Guid.TryParse(playerId, out var guid))
        {
            throw new InvalidOperationException("Player not found");
        }

        return Ok(await fetchPlayerDataUseCase.ExecuteAsync(guid));
    }
}