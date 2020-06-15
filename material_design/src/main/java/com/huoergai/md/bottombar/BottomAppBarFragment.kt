package com.huoergai.md.bottombar

import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomappbar.BottomAppBarTopEdgeTreatment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.shape.CutCornerTreatment
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.snackbar.Snackbar
import com.huoergai.md.R
import com.huoergai.md.fragment.IonBackPressed
import com.huoergai.md.themeswitcher.ThemeSwitcherHelper

/**
 * D&T: 2020-06-15 04:49 PM
 * Des:
 */
class BottomAppBarFragment : Fragment(), IonBackPressed {

    private lateinit var themeSwitchHelper: ThemeSwitcherHelper
    private lateinit var coordinator: CoordinatorLayout
    private lateinit var fab: FloatingActionButton
    private lateinit var bar: BottomAppBar
    private lateinit var bottomDrawerBehavior: BottomSheetBehavior<View>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
        themeSwitchHelper = ThemeSwitcherHelper(fragmentManager)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.demo_primary, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // return super.onOptionsItemSelected(item)
        showSnackerBar(item.title.toString())
        return true
    }

    private fun showSnackerBar(item: String) {
        Snackbar.make(coordinator, item, Snackbar.LENGTH_SHORT).setAnchorView(fab).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.cat_bottomappbar_fragment, container, false)

        val tb = view.findViewById<Toolbar>(R.id.toolbar)
        val btnCenter = view.findViewById<Button>(R.id.center)
        val btnEnd = view.findViewById<Button>(R.id.end)
        val btnAttachToggle = view.findViewById<ToggleButton>(R.id.attach_toggle)
        val btnScrollToggle = view.findViewById<ToggleButton>(R.id.bar_scroll_toggle)
        val navView = view.findViewById<NavigationView>(R.id.navigation_view)

        coordinator = view.findViewById(R.id.coordinator_layout)
        bar = view.findViewById(R.id.bar)
        fab = view.findViewById(R.id.fab)

        // setup toolbar
        // themeSwitchHelper.onCreateOptionsMenu(tb.menu, activity?.menuInflater)
        tb.title = arguments?.getString("title") ?: "foo"
        tb.setOnMenuItemClickListener(themeSwitchHelper::onOptionsItemSelected)
        tb.setNavigationOnClickListener { activity?.onBackPressed() }
        if (activity is AppCompatActivity) {
            (activity as AppCompatActivity).setSupportActionBar(bar)
        }

        // setup bottom drawer
        val bottomDrawer = coordinator.findViewById<FrameLayout>(R.id.bottom_drawer)
        bottomDrawerBehavior = BottomSheetBehavior.from(bottomDrawer)
        bottomDrawerBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        bar.setNavigationOnClickListener {
            bottomDrawerBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
            bar.setNavigationIcon(R.drawable.ic_drawer_menu_24px)
            bar.replaceMenu(R.menu.demo_primary)
        }

        fab.setOnClickListener {
            showSnackerBar(it.contentDescription.toString())
        }
        navView.setNavigationItemSelectedListener {
            showSnackerBar(it.title.toString())
            return@setNavigationItemSelectedListener false
        }

        btnAttachToggle.isChecked = (fab.visibility == View.VISIBLE)
        btnCenter.setOnClickListener {
            bar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
        }
        btnEnd.setOnClickListener { bar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END }
        btnAttachToggle.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked) {
                fab.show()
            } else {
                fab.hide()
            }
        }

        btnScrollToggle.isChecked = bar.hideOnScroll
        btnScrollToggle.setOnCheckedChangeListener { compoundButton, isChecked ->
            bar.hideOnScroll = isChecked
        }

        // setup bottom app bar shape appearance
        val sam = fab.shapeAppearanceModel
        val cutCornerFab =
            sam.bottomLeftCorner is CutCornerTreatment && sam.bottomRightCorner is CutCornerTreatment
        val topEdge: BottomAppBarTopEdgeTreatment = if (cutCornerFab) {
            BottomAppBarCutCornersTopEdge(
                bar.fabCradleMargin, bar.fabCradleRoundedCornerRadius, bar.cradleVerticalOffset
            )
        } else {
            BottomAppBarTopEdgeTreatment(
                bar.fabCradleMargin, bar.fabCradleRoundedCornerRadius, bar.cradleVerticalOffset
            )
        }
        val barBackground: MaterialShapeDrawable = bar.background as MaterialShapeDrawable
        barBackground.shapeAppearanceModel = barBackground.shapeAppearanceModel
            .toBuilder()
            .setTopEdge(topEdge)
            .build()


        return view
    }

    override fun onBackPressed(): Boolean {
        if (bottomDrawerBehavior.state != BottomSheetBehavior.STATE_HIDDEN) {
            bottomDrawerBehavior.state = BottomSheetBehavior.STATE_HIDDEN
            return true
        }
        return false
    }

}