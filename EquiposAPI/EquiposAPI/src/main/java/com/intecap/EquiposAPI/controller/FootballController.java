package com.intecap.EquiposAPI.controller;

import com.intecap.EquiposAPI.dto.LeagueDto;
import com.intecap.EquiposAPI.dto.MatchDto;
import com.intecap.EquiposAPI.service.FootballService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@Validated
public class FootballController {
  private final FootballService service;
  public FootballController(FootballService service) { this.service = service; }

  @GetMapping("/health")
  public Map<String, Object> health() { return Map.of("ok", true); }

  @Operation(summary = "Lista de ligas soportadas") @SecurityRequirement(name = "basicAuth")
  @GetMapping("/api/leagues")
  public List<LeagueDto> leagues() {
    return List.of(
      new LeagueDto(39, "Premier League", "England"),
      new LeagueDto(140, "La Liga", "Spain"),
      new LeagueDto(135, "Serie A", "Italy")
    );
  }

  @Operation(summary = "Partidos por liga y fecha (YYYY-MM-DD)") @SecurityRequirement(name = "basicAuth")
  @GetMapping("/api/matches")
  public ResponseEntity<?> matches(@RequestParam @NotBlank String leagueId, @RequestParam @NotBlank String date) {
    try {
      List<MatchDto> list = service.getMatches(leagueId, date);
      return ResponseEntity.ok(Map.of("source", service.hasLiveApi()? "apifootball":"mock",
                                      "leagueId", leagueId, "date", date, "matches", list));
    } catch (Exception e) {
      return ResponseEntity.status(502).body(Map.of("error","Upstream error","detail", e.getMessage()));
    }
  }
}
