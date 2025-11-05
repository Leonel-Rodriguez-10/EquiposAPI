package com.intecap.EquiposAPI.service;

import com.intecap.EquiposAPI.dto.MatchDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class FootballService {
  @Value("${football.api.base:https://v3.football.api-sports.io}") private String apiBase;
  @Value("${football.api.key:}") private String apiKey;
  private final RestTemplate rest = new RestTemplate();

  public boolean hasLiveApi() { return apiKey != null && !apiKey.isBlank(); }

  @SuppressWarnings("unchecked")
  public List<MatchDto> getMatches(String leagueId, String date) {
    if (!hasLiveApi()) {
      List<MatchDto> mock = new ArrayList<>();
      mock.add(new MatchDto(1L, date + "T18:00:00Z", "NS", "Equipo A", "Equipo B", null, null));
      mock.add(new MatchDto(2L, date + "T20:00:00Z", "NS", "Equipo C", "Equipo D", null, null));
      return mock;
    }
    String url = String.format("%s/fixtures?league=%s&date=%s", apiBase, leagueId, date);
    HttpHeaders headers = new HttpHeaders(); headers.set("x-apisports-key", apiKey);
    ResponseEntity<Map> resp = rest.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), Map.class);
    List<Map<String,Object>> arr = (List<Map<String,Object>>) resp.getBody().get("response");
    List<MatchDto> out = new ArrayList<>();
    for (Map<String, Object> fx : arr) {
      Map<String,Object> fixture = (Map<String,Object>) fx.get("fixture");
      Map<String,Object> status = (Map<String,Object>) fixture.get("status");
      Map<String,Object> teams = (Map<String,Object>) fx.get("teams");
      Map<String,Object> home = (Map<String,Object>) teams.get("home");
      Map<String,Object> away = (Map<String,Object>) teams.get("away");
      Map<String,Object> goals = (Map<String,Object>) fx.get("goals");
      Long id = fixture.get("id") instanceof Number n ? n.longValue() : null;
      String utcKickoff = (String) fixture.get("date");
      String st = status != null ? (String) status.get("short") : null;
      String homeName = (String) home.get("name");
      String awayName = (String) away.get("name");
      Integer hs = goals.get("home")==null?null:((Number)goals.get("home")).intValue();
      Integer as = goals.get("away")==null?null:((Number)goals.get("away")).intValue();
      out.add(new MatchDto(id, utcKickoff, st, homeName, awayName, hs, as));
    }
    return out;
  }
}
