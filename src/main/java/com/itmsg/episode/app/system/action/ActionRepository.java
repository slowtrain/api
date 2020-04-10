package com.itmsg.episode.app.system.action;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ActionRepository extends JpaRepository<Action, Long> {
    List<Action> findByActionId(String actionId);
    List<Action> findByObjectId(String objectId);
}
