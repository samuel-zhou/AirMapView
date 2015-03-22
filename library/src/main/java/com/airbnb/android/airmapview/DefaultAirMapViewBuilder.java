package com.airbnb.android.airmapview;

import android.content.Context;

import com.amazon.geo.mapsv2.util.AmazonMapsRuntimeUtil;
import com.google.android.gms.common.GooglePlayServicesUtil;

/**
 * Use this class to request an AirMapView builder.
 */
public class DefaultAirMapViewBuilder {

    private final boolean isAmazonMapsAvailable;
    private final boolean isGooglePlayServicesAvailable;

    /**
     * Default {@link DefaultAirMapViewBuilder} constructor.
     *
     * @param context The application context.
     */
    public DefaultAirMapViewBuilder(Context context) {
        this(GooglePlayServicesUtil.isGooglePlayServicesAvailable(context) == com.google.android.gms.common.ConnectionResult.SUCCESS,
                AmazonMapsRuntimeUtil.isAmazonMapsRuntimeAvailable(context) == com.amazon.geo.mapsv2.util.ConnectionResult.SUCCESS);
    }

    /**
     * @param isGooglePlayServicesAvailable Whether or not Google Play services is available on the
     *                                      device. If you set this to true and it is not available,
     *                                      bad things can happen.
     * @param isAmazonMapsAvailable Whether or not the Amazon runtime is available on the
     *                                      device. If you set this to true and it is not available,
     *                                      bad things can happen.
     */
    public DefaultAirMapViewBuilder(boolean isGooglePlayServicesAvailable, boolean isAmazonMapsAvailable) {
        this.isGooglePlayServicesAvailable = isGooglePlayServicesAvailable;
        this.isAmazonMapsAvailable = isAmazonMapsAvailable;
    }

    /**
     * Returns the first/default supported AirMapView implementation in order of preference, as
     * defined by {@link AirMapViewTypes}.
     */
    public AirMapViewBuilder builder() {
        if (isGooglePlayServicesAvailable)
            return new GoogleNativeAirMapViewBuilder();
        if (isAmazonMapsAvailable)
            return new AmazonNativeAirMapViewBuilder();
        return new WebAirMapViewBuilder();
    }

    /**
     * Returns the AirMapView implementation as requested by the mapType argument.
     * Use this method if you need to request a specific AirMapView implementation that is not
     * necessarily the preferred type. For example, you can use it to explicit request a web-based
     * map implementation.
     *
     * @param mapType Map type for the requested AirMapView implementation.
     * @return An {@link AirMapViewBuilder} for the requested {@link AirMapViewTypes} mapType.
     */
    public AirMapViewBuilder builder(AirMapViewTypes mapType) {
        switch (mapType) {
            case GOOGLE_NATIVE:
                if (isGooglePlayServicesAvailable)
                    return new GoogleNativeAirMapViewBuilder();
                break;
            case AMAZON_NATIVE:
                if (isAmazonMapsAvailable)
                    return new AmazonNativeAirMapViewBuilder();
                break;
            case WEB:
                return new WebAirMapViewBuilder();
        }
        throw new UnsupportedOperationException("Requested map type not supported: " + mapType.name());
    }

}
