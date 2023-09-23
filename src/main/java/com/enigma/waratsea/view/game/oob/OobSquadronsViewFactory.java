package com.enigma.waratsea.view.game.oob;

import com.enigma.waratsea.model.squadron.DeploymentState;

public interface OobSquadronsViewFactory {
  OobSquadronsView get(DeploymentState deploymentState);
}
