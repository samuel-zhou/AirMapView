package com.airbnb.android.airmapview;

import android.os.Bundle;

import com.amazon.geo.mapsv2.AmazonMapOptions;
import com.amazon.geo.mapsv2.model.CameraPosition;

public class AirAmazonMapOptions {

    private AmazonMapOptions options;

    public AirAmazonMapOptions(AmazonMapOptions options) {
        this.options = options;
    }

    public AirAmazonMapOptions zOrderOnTop(boolean zOrderOnTop) {
        options.zOrderOnTop(zOrderOnTop);
        return this;
    }

    public AirAmazonMapOptions useViewLifecycleInFragment(boolean useViewLifecycleInFragment) {
        options.useViewLifecycleInFragment(useViewLifecycleInFragment);
        return this;
    }

    public AirAmazonMapOptions mapType(int mapType) {
        options.mapType(mapType);
        return this;
    }

    public AirAmazonMapOptions camera(CameraPosition camera) {
        options.camera(camera);
        return this;
    }

    public AirAmazonMapOptions zoomControlsEnabled(boolean enabled) {
        options.zoomControlsEnabled(enabled);
        return this;
    }

    public AirAmazonMapOptions compassEnabled(boolean enabled) {
        options.compassEnabled(enabled);
        return this;
    }

    public AirAmazonMapOptions scrollGesturesEnabled(boolean enabled) {
        options.scrollGesturesEnabled(enabled);
        return this;
    }

    public AirAmazonMapOptions zoomGesturesEnabled(boolean enabled) {
        options.zoomGesturesEnabled(enabled);
        return this;
    }

    public AirAmazonMapOptions tiltGesturesEnabled(boolean enabled) {
        options.tiltGesturesEnabled(enabled);
        return this;
    }

    public AirAmazonMapOptions rotateGesturesEnabled(boolean enabled) {
        options.rotateGesturesEnabled(enabled);
        return this;
    }

    public AirAmazonMapOptions liteMode(boolean enabled) {
        options.liteMode(enabled);
        return this;
    }

    public AirAmazonMapOptions mapToolbarEnabled(boolean enabled) {
        options.mapToolbarEnabled(enabled);
        return this;
    }

    public Boolean getZOrderOnTop() {
        return options.getZOrderOnTop();
    }

    public Boolean getUseViewLifecycleInFragment() {
        return options.getUseViewLifecycleInFragment();
    }

    public int getMapType() {
        return options.getMapType();
    }

    public CameraPosition getCamera() {
        return options.getCamera();
    }

    public Boolean getZoomControlsEnabled() {
        return options.getZoomControlsEnabled();
    }

    public Boolean getCompassEnabled() {
        return options.getCompassEnabled();
    }

    public Boolean getScrollGesturesEnabled() {
        return options.getScrollGesturesEnabled();
    }

    public Boolean getZoomGesturesEnabled() {
        return options.getZoomGesturesEnabled();
    }

    public Boolean getTiltGesturesEnabled() {
        return options.getTiltGesturesEnabled();
    }

    public Boolean getRotateGesturesEnabled() {
        return options.getRotateGesturesEnabled();
    }

    public Boolean getLiteMode() {
        return options.getLiteMode();
    }

    public Boolean getMapToolbarEnabled() {
        return options.getMapToolbarEnabled();
    }

    public Bundle toBundle() {
        Bundle args = new Bundle();
        // this is internal to SupportMapFragment
        args.putParcelable("MapOptions", options);
        return args;
    }
}
