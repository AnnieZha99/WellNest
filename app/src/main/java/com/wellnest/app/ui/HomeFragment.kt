package com.wellnest.app.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import com.wellnest.app.R
import com.wellnest.app.databinding.FragmentHomeBinding

class HomeFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var googleMap: GoogleMap

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1002
        private const val TAG = "HomeFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        // Find the SupportMapFragment by its ID
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapFragment) as? SupportMapFragment
        if (mapFragment != null) {
            Log.d(TAG, "MapFragment found")
            mapFragment.getMapAsync(this)
        } else {
            Log.e(TAG, "MapFragment is null")
        }

        // Set up search view
        binding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    // TODO: Implement search functionality
                    Toast.makeText(context, "Searching for: $it", Toast.LENGTH_SHORT).show()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // No action needed for text changes
                return false
            }
        })

        // Set up view details button
        binding.viewDetailsButton.setOnClickListener {
            // TODO: Navigate to details screen
            Toast.makeText(context, "View details clicked", Toast.LENGTH_SHORT).show()
        }

        return view
    }

    override fun onMapReady(map: GoogleMap) {
        Log.d(TAG, "Map is ready")
        googleMap = map

        // Enable UI settings
        googleMap.uiSettings.isZoomControlsEnabled = true
        googleMap.uiSettings.isCompassEnabled = true
        // Enable the My Location button (appears as a button allowing user to jump to current location)
        googleMap.uiSettings.isMyLocationButtonEnabled = true

        // Check and request location permissions to enable the user's location on the map
        checkLocationPermission()

        // Add a marker and move the camera
        val sampleLocation = LatLng(40.7128, -74.0060) // New York City coordinates
        googleMap.addMarker(MarkerOptions().position(sampleLocation).title("New York City"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sampleLocation, 10f))

        // Add padding to the map to account for the bottom navigation and info card
        val bottomPadding = resources.getDimensionPixelSize(R.dimen.activity_vertical_margin) * 3
        googleMap.setPadding(0, 0, 0, bottomPadding)
    }

    private fun checkLocationPermission() {
        val context = requireContext()
        val fineLocationPermission = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
        val coarseLocationPermission = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)

        if (fineLocationPermission == PackageManager.PERMISSION_GRANTED) {
            // Precise location access granted
            googleMap.isMyLocationEnabled = true
        } else if (coarseLocationPermission == PackageManager.PERMISSION_GRANTED) {
            // Approximate location access granted
            googleMap.isMyLocationEnabled = true
        } else {
            // Request location permissions
            requestPermissions(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty()) {
                val fineLocationIndex = permissions.indexOf(Manifest.permission.ACCESS_FINE_LOCATION)
                val coarseLocationIndex = permissions.indexOf(Manifest.permission.ACCESS_COARSE_LOCATION)

                val fineLocationGranted = fineLocationIndex >= 0 && grantResults[fineLocationIndex] == PackageManager.PERMISSION_GRANTED
                val coarseLocationGranted = coarseLocationIndex >= 0 && grantResults[coarseLocationIndex] == PackageManager.PERMISSION_GRANTED

                if (fineLocationGranted || coarseLocationGranted) {
                    if (::googleMap.isInitialized) {
                        try {
                            googleMap.isMyLocationEnabled = true
                        } catch (e: SecurityException) {
                            e.printStackTrace()
                        }
                    }
                } else {
                    Toast.makeText(requireContext(), "Location permission is needed to show your location on the map", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 