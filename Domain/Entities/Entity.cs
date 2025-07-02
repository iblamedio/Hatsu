namespace Domain.Entities;

public abstract class Entity<T>
{
    public T Id { get; init; } = default!;
    public DateTimeOffset CreatedAt { get; init; }
    public DateTimeOffset? UpdatedAt { get; protected set; }
}