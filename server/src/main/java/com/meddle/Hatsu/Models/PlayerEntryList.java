package com.meddle.Hatsu.Models;

import java.util.List;

public record PlayerEntryList(List<Entry> planning, List<Entry> playing, List<Entry> completed, List<Entry> paused,
      List<Entry> dropped) {

};
