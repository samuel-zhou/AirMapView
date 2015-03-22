package com.airbnb.android.airmapview;

import android.graphics.Point;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.android.airmapview.listeners.InfoWindowCreator;
import com.airbnb.android.airmapview.listeners.OnCameraChangeListener;
import com.airbnb.android.airmapview.listeners.OnInfoWindowClickListener;
import com.airbnb.android.airmapview.listeners.OnMapBoundsCallback;
import com.airbnb.android.airmapview.listeners.OnMapClickListener;
import com.airbnb.android.airmapview.listeners.OnMapLoadedListener;
import com.airbnb.android.airmapview.listeners.OnMapMarkerClickListener;
import com.amazon.geo.mapsv2.AmazonMap;
import com.amazon.geo.mapsv2.CameraUpdateFactory;
import com.amazon.geo.mapsv2.OnMapReadyCallback;
import com.amazon.geo.mapsv2.Projection;
import com.amazon.geo.mapsv2.SupportMapFragment;
import com.amazon.geo.mapsv2.UiSettings;
import com.amazon.geo.mapsv2.model.CameraPosition;
import com.amazon.geo.mapsv2.model.CircleOptions;
import com.amazon.geo.mapsv2.model.LatLngBounds;
import com.amazon.geo.mapsv2.model.Marker;
import com.google.android.gms.maps.GoogleMap;

public class NativeAmazonMapFragment extends SupportMapFragment implements AirMapInterface {

    private OnMapLoadedListener mOnMapLoadedListener;
    private AmazonMap mAmazonMap;

    public static NativeAmazonMapFragment newInstance(AirAmazonMapOptions options) {
        return new NativeAmazonMapFragment().setArguments(options);
    }

    public NativeAmazonMapFragment setArguments(AirAmazonMapOptions options) {
        setArguments(options.toBundle());
        return this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);

        init();

        return v;
    }

    public void init() {
        getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(AmazonMap AmazonMap) {
                if (AmazonMap != null && getActivity() != null) {
                    mAmazonMap = AmazonMap;
                    UiSettings settings = mAmazonMap.getUiSettings();
                    settings.setZoomControlsEnabled(false);
                    settings.setMyLocationButtonEnabled(false);
                    setMyLocationEnabled(true);

                    if (mOnMapLoadedListener != null) {
                        mOnMapLoadedListener.onMapLoaded();
                    }
                }
            }
        });
    }

    @Override
    public boolean isInitialized() {
        return mAmazonMap != null && getActivity() != null;
    }

    @Override
    public void clearMarkers() {
        mAmazonMap.clear();
    }

    @Override
    public void addMarker(AirMapMarker airMarker) {
        airMarker.addToMap(mAmazonMap);
    }

    @Override
    public void removeMarker(AirMapMarker marker) {
        marker.removeFromMap();
    }

    @Override
    public void setOnInfoWindowClickListener(final OnInfoWindowClickListener listener) {
        // TODO 
//        mAmazonMap.setOnInfoWindowClickListener(new AmazonMap.OnInfoWindowClickListener() {
//            @Override
//            public void onInfoWindowClick(Marker marker) {
//                listener.onInfoWindowClick(marker);
//            }
//        });
    }

    @Override
    public void setInfoWindowCreator(GoogleMap.InfoWindowAdapter adapter, InfoWindowCreator creator) {
        // TODO implement this
    }

    public void setInfoWindowCreator(AmazonMap.InfoWindowAdapter adapter, InfoWindowCreator creator) {
        // TODO
//        mAmazonMap.setInfoWindowAdapter(adapter);
    }

    @Override
    public void drawCircle(com.google.android.gms.maps.model.LatLng latLng, int radius) {
        drawCircle(latLng, radius, CIRCLE_BORDER_COLOR);
    }

    @Override
    public void drawCircle(com.google.android.gms.maps.model.LatLng latLng, int radius, int borderColor) {
        drawCircle(latLng, radius, borderColor, CIRCLE_BORDER_WIDTH);
    }

    @Override
    public void drawCircle(com.google.android.gms.maps.model.LatLng latLng, int radius, int borderColor, int borderWidth) {
        drawCircle(latLng, radius, borderColor, borderWidth, CIRCLE_FILL_COLOR);
    }

    @Override
    public void drawCircle(com.google.android.gms.maps.model.LatLng latLng, int radius, int borderColor, int borderWidth, int fillColor) {
        mAmazonMap.addCircle(new CircleOptions()
                .center(toAmazonLatLng(latLng))
                .strokeColor(borderColor)
                .strokeWidth(borderWidth)
                .fillColor(fillColor)
                .radius(radius));
    }

    @Override
    public void getMapScreenBounds(OnMapBoundsCallback callback) {
        final Projection projection = mAmazonMap.getProjection();
        int hOffset = getResources().getDimensionPixelOffset(R.dimen.map_horizontal_padding);
        int vOffset = getResources().getDimensionPixelOffset(R.dimen.map_vertical_padding);

        LatLngBounds.Builder builder = LatLngBounds.builder();
        builder.include(projection.fromScreenLocation(new Point(hOffset, vOffset))); // top-left
        builder.include(projection.fromScreenLocation(new Point(getView().getWidth() - hOffset, vOffset))); // top-right
        builder.include(projection.fromScreenLocation(new Point(hOffset, getView().getHeight() - vOffset))); // bottom-left
        builder.include(projection.fromScreenLocation(new Point(getView().getWidth() - hOffset, getView().getHeight() - vOffset))); // bottom-right

        // TODO make OnMapBoundsCallback take our own model instead of Google's
        LatLngBounds amazonBounds = builder.build();
        callback.onMapBoundsReady(new com.google.android.gms.maps.model.LatLngBounds(toGmsLatLng(amazonBounds.southwest), toGmsLatLng(amazonBounds.northeast)));
    }

    @Override
    public void setCenter(com.google.android.gms.maps.model.LatLngBounds latLngBounds, int boundsPadding) {
        mAmazonMap.moveCamera(CameraUpdateFactory.newLatLngBounds(toAmazonLatLngBounds(latLngBounds), boundsPadding));
    }

    private LatLngBounds toAmazonLatLngBounds(com.google.android.gms.maps.model.LatLngBounds latLngBounds) {
        return new LatLngBounds(toAmazonLatLng(latLngBounds.southwest), toAmazonLatLng(latLngBounds.northeast));
    }

    @Override
    public void setZoom(int zoom) {
        mAmazonMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mAmazonMap.getCameraPosition().target, zoom));
    }

    @Override
    public void animateCenter(com.google.android.gms.maps.model.LatLng latLng) {
        mAmazonMap.animateCamera(CameraUpdateFactory.newLatLng(toAmazonLatLng(latLng)));
    }

    @Override
    public void setCenter(com.google.android.gms.maps.model.LatLng latLng) {
        mAmazonMap.moveCamera(CameraUpdateFactory.newLatLng(toAmazonLatLng(latLng)));
    }

    @Override
    public com.google.android.gms.maps.model.LatLng getCenter() {
        return toGmsLatLng(mAmazonMap.getCameraPosition().target);
    }

    @Override
    public int getZoom() {
        return (int) mAmazonMap.getCameraPosition().zoom;
    }

    @Override
    public void setOnCameraChangeListener(final OnCameraChangeListener onCameraChangeListener) {
        mAmazonMap.setOnCameraChangeListener(new AmazonMap.OnCameraChangeListener() {

            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                // camera change can occur programatically.
                if (isResumed()) {
                    com.google.android.gms.maps.model.LatLng center = new com.google.android.gms.maps.model.LatLng(cameraPosition.target.latitude, cameraPosition.target.longitude);
                    onCameraChangeListener.onCameraChanged(center, (int) cameraPosition.zoom);
                }
            }
        });
    }

    @Override
    public void setOnMapLoadedListener(OnMapLoadedListener onMapLoadedListener) {
        mOnMapLoadedListener = onMapLoadedListener;
    }

    @Override
    public void setCenterZoom(com.google.android.gms.maps.model.LatLng latLng, int zoom) {
        mAmazonMap.moveCamera(CameraUpdateFactory.newLatLngZoom(toAmazonLatLng(latLng), zoom));
    }

    @Override
    public void animateCenterZoom(com.google.android.gms.maps.model.LatLng latLng, int zoom) {
        mAmazonMap.animateCamera(CameraUpdateFactory.newLatLngZoom(toAmazonLatLng(latLng), zoom));
    }

    @Override
    public void setOnMarkerClickListener(final OnMapMarkerClickListener listener) {
        mAmazonMap.setOnMarkerClickListener(new AmazonMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                // TODO
//                listener.onMapMarkerClick(marker);
                return false;
            }
        });
    }

    @Override
    public void setOnMapClickListener(final OnMapClickListener listener) {
        mAmazonMap.setOnMapClickListener(new AmazonMap.OnMapClickListener() {
            @Override
            public void onMapClick(com.amazon.geo.mapsv2.model.LatLng latLng) {
                listener.onMapClick(toGmsLatLng(latLng));
            }
        });
    }

    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        mAmazonMap.setPadding(left, top, right, bottom);
    }

    @Override
    public void setMyLocationEnabled(boolean enabled) {
        mAmazonMap.setMyLocationEnabled(enabled);
    }

    @Override
    public void addPolyline(AirMapPolyline polyline) {
        polyline.addToMap(mAmazonMap);
    }

    @Override
    public void removePolyline(AirMapPolyline polyline) {
        polyline.removeFromMap();
    }

    /**
     * This method will return the Amazon map if initialized. Will return null otherwise
     *
     * @return returns Amazon map if initialized
     */
    public AmazonMap getAmazonMap() {
        return mAmazonMap;
    }

    public com.google.android.gms.maps.model.LatLng toGmsLatLng(com.amazon.geo.mapsv2.model.LatLng latLng) {
        return new com.google.android.gms.maps.model.LatLng(latLng.latitude, latLng.longitude);
    }

    private com.amazon.geo.mapsv2.model.LatLng toAmazonLatLng(com.google.android.gms.maps.model.LatLng latLng) {
        return new com.amazon.geo.mapsv2.model.LatLng(latLng.latitude, latLng.longitude);
    }
}
