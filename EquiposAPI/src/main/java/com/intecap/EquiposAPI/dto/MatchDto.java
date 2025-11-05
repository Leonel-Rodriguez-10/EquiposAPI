package com.intecap.EquiposAPI.dto;
public record MatchDto(Long id, String utcKickoff, String status, String home, String away, Integer homeScore, Integer awayScore) {}