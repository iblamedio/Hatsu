using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;
using System.Text;
using Application.Interfaces;
using Microsoft.Extensions.Configuration;
using Microsoft.IdentityModel.Tokens;

namespace Services;

public class JwtService(IConfiguration config) : IJwtService
{
    public string GenerateJwtToken(Guid playerId)
    {
        var claims = new List<Claim>
        {
            new Claim(ClaimTypes.NameIdentifier, playerId.ToString())
        };

        var key = new SymmetricSecurityKey(Encoding.UTF8.GetBytes(config["Jwt:Key"]!));

        var token = new JwtSecurityToken(issuer: config["Jwt:Issuer"], audience: config["Jwt:Audience"], claims: claims,
            expires: DateTime.UtcNow.AddSeconds(double.Parse(config["Jwt:ExpiresInSeconds"]!)),
            signingCredentials: new SigningCredentials(key, SecurityAlgorithms.HmacSha256));

        return new JwtSecurityTokenHandler().WriteToken(token);
    }
}