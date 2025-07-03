using Domain.Entities;
using Domain.Enums;

namespace Domain.ValueObjects;

public record class Playlists
{
    private Playlists(IEnumerable<Entry> planning, IEnumerable<Entry> playing, IEnumerable<Entry> completed, IEnumerable<Entry> paused, IEnumerable<Entry> dropped)
    {
        Planning = planning;
        Playing = playing;
        Completed = completed;
        Paused = paused;
        Dropped = dropped;
    }

    public IEnumerable<Entry> Planning { get; private set; }
    public IEnumerable<Entry> Playing { get; private set; }
    public IEnumerable<Entry> Completed { get; private set; }
    public IEnumerable<Entry> Paused { get; private set; }
    public IEnumerable<Entry> Dropped { get; private set; }

    public static Playlists FromEntries(IEnumerable<Entry> entries)
    {
        var entryList = entries.ToList();
        
        return new Playlists(entryList.Where<Entry>(e => e.Status.Equals(Status.Planning)),
            entryList.Where<Entry>(e => e.Status.Equals(Status.Playing)),
            entryList.Where<Entry>(e => e.Status.Equals(Status.Completed)),
            entryList.Where<Entry>(e => e.Status.Equals(Status.Paused)),
            entryList.Where<Entry>(e => e.Status.Equals(Status.Dropped)) );
    }
}