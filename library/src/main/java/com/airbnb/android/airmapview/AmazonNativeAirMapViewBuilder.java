package com.airbnb.android.airmapview;

import com.amazon.geo.mapsv2.AmazonMapOptions;

/**
 * Map provider that uses native Amazon Maps implementation.

 * In order to use this, the Amazon Runtime must be available on the device
 */

public class AmazonNativeAirMapViewBuilder
        implements AirMapViewBuilder<NativeAmazonMapFragment, AirAmazonMapOptions> {

    private AirAmazonMapOptions options;

    @Override
    public AirMapViewBuilder<NativeAmazonMapFragment, AirAmazonMapOptions> withOptions(AirAmazonMapOptions options) {
        this.options = options;
        return this;
    }

    /**
     * Build the map fragment with the requested options
     * @return The {@link NativeAmazonMapFragment} map fragment.
     */
    @Override
    public NativeAmazonMapFragment build() {
        if (options == null) {
             options = new AirAmazonMapOptions(new AmazonMapOptions());
        }
        return NativeAmazonMapFragment.newInstance(options);
    }
}
