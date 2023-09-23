package com.enigma.waratsea.viewmodel.game.oob;

import com.enigma.waratsea.model.squadron.DeploymentState;

public interface OobSquadronsViewModelFactory {
  OobSquadronsViewModel get(DeploymentState deploymentState);
}
