CREATE VIEW match_view AS
SELECT
    match.id, a.nickname visitor, visiting_team_score v_pts,
    b.nickname home, home_team_score h_pts,
    neutral_site,
    week,
    kickoff_date_time
 FROM
    match inner join team a on a.id=match.visiting_team_id
    inner join team b on b.id=match.home_team_id
 order by kickoff_date_time;
