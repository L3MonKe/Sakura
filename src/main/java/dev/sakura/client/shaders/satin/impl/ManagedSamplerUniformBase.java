/*
 * Satin
 * Copyright (C) 2019-2024 Ladysnake
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; If not, see <https://www.gnu.org/licenses>.
 */
package dev.sakura.client.shaders.satin.impl;

import com.mojang.logging.LogUtils;
import dev.sakura.client.shaders.satin.api.uniform.SamplerUniform;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.gl.ShaderProgramDefinition;

import java.util.List;

public abstract class ManagedSamplerUniformBase extends ManagedUniformBase implements SamplerUniform {
    protected SamplerAccess[] targets = new SamplerAccess[0];
    protected int[] locations = new int[0];
    protected Object cachedValue;

    public ManagedSamplerUniformBase(String name) {
        super(name);
    }

    private int getSamplerLoc(SamplerAccess access) {
        List<ShaderProgramDefinition.Sampler> samplers = access.getSamplers();
        for (int i = 0; i < samplers.size(); i++) {
            if (samplers.get(i).name().equals(this.name)) {
                return access.getSamplerLocations().getInt(i);
            }
        }
        return -1;
    }

    @Override
    public boolean findUniformTarget(ShaderProgram shader) {
        LogUtils.getLogger().debug("Finding sampler {} in shader", this.name);
        return findUniformTarget1(((SamplerAccess) shader));
    }

    private boolean findUniformTarget1(SamplerAccess access) {
        if (access.sakura$hasSampler(this.name)) {
            this.targets = new SamplerAccess[]{access};
            this.locations = new int[]{getSamplerLoc(access)};
            this.syncCurrentValues();
            return true;
        }
        return false;
    }

    private void syncCurrentValues() {
        Object value = this.cachedValue;
        if (value != null) { // after the first upload
            this.cachedValue = null;
            this.set(value);
        }
    }

    protected abstract void set(Object value);

}
