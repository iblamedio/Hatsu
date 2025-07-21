using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;
using System.Text;
using Application.Interfaces;
using Microsoft.IdentityModel.Tokens;

namespace Services;

public class JwtService : IJwtService
{
    public string GenerateJwtToken(Guid playerId)
    {
        var claims = new List<Claim>
        {
            new Claim(ClaimTypes.NameIdentifier, playerId.ToString())
        };
            
            var key = new SymmetricSecurityKey(Encoding.UTF8.GetBytes("SECRET_KEY"));
            var creds = new SigningCredentials(key, SecurityAlgorithms.HmacSha256);

            var token = new JwtSecurityToken(issuer: "Hatsu", claims: claims, expires: DateTime.UtcNow.AddDays(15), signingCredentials: creds);
            
            return new JwtSecurityTokenHandler().WriteToken(token);
    }
}