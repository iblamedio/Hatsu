using System.Security.Claims;
using Api.DTOs;
using Application.UseCases.EntryUseCases.CreateEntryUseCase;
using Application.UseCases.EntryUseCases.DeleteEntryUseCase;
using Application.UseCases.EntryUseCases.GetEntriesByUser;
using Application.UseCases.EntryUseCases.UpdateEntryUseCase;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

namespace Api.Controllers;

[Authorize]
[ApiController]
[Route("api/[controller]")]
public class EntryController(
    CreateEntryUseCase createEntryUseCase,
    GetEntriesByUserUseCase getEntriesByUserUseCase,
    DeleteEntryUseCase deleteEntryUseCase,
    UpdateEntryUseCase updateEntryUseCase
    ) : ControllerBase
{
    [HttpPost]
    public async Task<IActionResult> New([FromBody] CreateEntryRequest request)
    {
        var userId = User.FindFirst(ClaimTypes.NameIdentifier)?.Value;
        if (!Guid.TryParse(userId, out var guid))
        {
            return Unauthorized();
        }

        return Ok(await createEntryUseCase.ExecuteAsync(new CreateEntryUseCaseInput(guid, request.GameId,request.Score, request.Status)));
    }

    [HttpGet]
    public async Task<IActionResult> Get([FromQuery] string id)
    {
        var userId = User.FindFirst(ClaimTypes.NameIdentifier)?.Value;
        if (!Guid.TryParse(userId, out var guid))
        {
            return Unauthorized();
        }
        
        return Ok(await getEntriesByUserUseCase.ExecuteAsync(guid));
    }

    [HttpPut]
    public async Task<IActionResult> Update([FromBody] EditEntryRequest request)
    {
        var userId = User.FindFirst(ClaimTypes.NameIdentifier)?.Value;
        if (!Guid.TryParse(userId, out var guid))
        {
            return Unauthorized();
        }

        await updateEntryUseCase.ExecuteAsync(new UpdateEntryUseCaseInput(guid, request.EntryId, request.Status, request.Score));
        
        return Ok();
    }
    
    [HttpDelete]
    public async Task<IActionResult> Destroy([FromQuery] string entryId)
    {
        var userId = User.FindFirst(ClaimTypes.NameIdentifier)?.Value;
        if (!Guid.TryParse(userId, out var userGuid))
        {
            return Unauthorized();
        }
        if (!Guid.TryParse(entryId, out var entryGuid))
        {
            return BadRequest();
        }

        await deleteEntryUseCase.ExecuteAsync(entryGuid, userGuid);
        
        return Ok();
    }
}