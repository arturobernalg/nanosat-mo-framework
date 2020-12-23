/*
 *  ----------------------------------------------------------------------------
 *  Copyright (C) 2021      European Space Agency
 *                          European Space Operations Centre
 *                          Darmstadt
 *                          Germany
 *  ----------------------------------------------------------------------------
 *  System                : ESA NanoSat MO Framework
 *  ----------------------------------------------------------------------------
 *  Licensed under the European Space Agency Public License, Version 2.0
 *  You may not use this file except in compliance with the License.
 * 
 *  Except as expressly set forth in this License, the Software is provided to
 *  You on an "as is" basis and without warranties of any kind, including without
 *  limitation merchantability, fitness for a particular purpose, absence of
 *  defects or errors, accuracy or non-infringement of intellectual property rights.
 *  
 *  See the License for the specific language governing permissions and
 *  limitations under the License. 
 *  ----------------------------------------------------------------------------
 */
package opssat.simulator.orekit;

import org.orekit.propagation.SpacecraftState;
import org.orekit.propagation.analytical.tle.TLEPropagator;

/**
 *
 * @author cezar
 */
public class GPSSatellite {
    String name;
    TLEPropagator propagator;
    private SpacecraftState state;

    public GPSSatellite(String name, TLEPropagator propagator) {
        this.name = name;
        this.propagator = propagator;
    }

    public SpacecraftState getState() {
        return state;
    }
    
    public void setState(SpacecraftState state) {
        this.state = state;
    }
    
    
    
}
