package com.zeta;

import com.zeta.client.Zeta;
import net.fabricmc.api.ClientModInitializer;

public class ZetaFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        Zeta.setInstance();
    }
}
