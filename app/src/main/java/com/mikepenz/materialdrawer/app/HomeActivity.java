package com.mikepenz.materialdrawer.app;

import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;

import com.mikepenz.materialdrawer.app.R;
import com.mikepenz.materialdrawer.app.OneFragment;
import com.mikepenz.materialdrawer.app.ThreeFragment;
import com.mikepenz.materialdrawer.app.TwoFragment;


import com.bumptech.glide.Glide;
import com.mikepenz.crossfadedrawerlayout.view.CrossfadeDrawerLayout;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.MiniDrawer;
import com.mikepenz.materialdrawer.holder.BadgeStyle;
import com.mikepenz.materialdrawer.interfaces.ICrossfader;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileSettingDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;
import com.mikepenz.materialdrawer.util.DrawerUIUtils;
import com.mikepenz.materialize.color.Material;
import com.mikepenz.materialize.util.UIUtils;

import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import static android.widget.Toast.LENGTH_LONG;

public class HomeActivity extends AppCompatActivity {


    DrawerLayout drawerLayout;
    NavigationView navigationView;
    FragmentManager FM;
    FragmentTransaction FT;


    private static final int PROFILE_SETTING = 1;
    //save our header or result
    private AccountHeader headerResult = null;
    private Drawer result = null;
    private CrossfadeDrawerLayout crossfadeDrawerLayout = null;

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int[] tabIcons = {
            R.mipmap.ic_chat,
            R.mipmap.ic_online,
            R.mipmap.ic_contacts
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLay);
        navigationView= (NavigationView) findViewById(R.id.shitstuff);

        FM= getSupportFragmentManager();
        FT= FM.beginTransaction();
        FT.replace(R.id.containerView, new TabFragment()).commit();

        // Handle Toolbar
        final  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//
//        viewPager = (ViewPager) findViewById(R.id.viewpager);
//        setupViewPager(viewPager);
//
//        tabLayout = (TabLayout) findViewById(R.id.tabs);
//        tabLayout.setupWithViewPager(viewPager);
//        setupTabIcons();
//
//        //set the back arrow in the toolbar
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle(R.string.app_name);

        // Create a few sample profile
        // NOTE you have to define the loader logic too. See the CustomApplication for more details
        final IProfile profile = new ProfileDrawerItem().withName("Arnold Zurbito").withEmail("zurbito_arnold@gmail.com").withIcon("https://avatars3.githubusercontent.com/u/1476232?v=3&s=460");
        final IProfile profile2 = new ProfileDrawerItem().withName("R-nold Zurb-2").withEmail("rnoldzurbito@github.com").withIcon(Uri.parse("https://avatars3.githubusercontent.com/u/887462?v=3&s=460"));
        final IProfile profile3 = new ProfileDrawerItem().withName("Superman").withEmail("superman@gmail.com").withIcon(R.drawable.profile3);
        final IProfile profile4 = new ProfileDrawerItem().withName("Mr. X").withEmail("misterx@gmail.com").withIcon(R.drawable.profile4);
        final IProfile profile5 = new ProfileDrawerItem().withName("Batman").withEmail("batman@gmail.com").withIcon(R.drawable.profile5);

        // Create the AccountHeader
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withCompactStyle(true)
                .withHeaderBackground(R.drawable.header)
                .addProfiles(
                        profile,
                        profile2,
                        profile3,
                        profile4,
                        profile5,
                        //don't ask but google uses 14dp for the add account icon in gmail but 20dp for the normal icons (like manage account)
                        new ProfileSettingDrawerItem().withName("Add Account").withDescription("Add new GitHub Account").withIcon(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_plus).actionBar().paddingDp(5).colorRes(R.color.material_drawer_dark_primary_text)).withIdentifier(PROFILE_SETTING),
                        new ProfileSettingDrawerItem().withName("Manage Account").withIcon(GoogleMaterial.Icon.gmd_settings)
                )
                .withSavedInstance(savedInstanceState)
                .build();

        //Create the drawer
        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withHasStableIds(true)
                .withDrawerLayout(R.layout.crossfade_drawer)
                .withDrawerWidthDp(72)
                .withGenerateMiniDrawer(true)
                .withAccountHeader(headerResult) //set the AccountHeader we created earlier for the header

                //this layout have to contain child layouts
//                .withRootView(R.id.drawer_container)
                .withActionBarDrawerToggleAnimated(true)

                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_item_compact_header).withIcon(FontAwesome.Icon.faw_home).withIdentifier(1),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_action_bar_drawer).withIcon(FontAwesome.Icon.faw_weixin).withBadge("22").withBadgeStyle(new BadgeStyle(Color.RED, Color.RED)).withIdentifier(2).withSelectable(false),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_multi_drawer).withIcon(FontAwesome.Icon.faw_users).withIdentifier(3),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_non_translucent_status_drawer).withIcon(FontAwesome.Icon.faw_eye).withIdentifier(4),
                        new PrimaryDrawerItem().withDescription("A more complex sample").withName(R.string.drawer_item_advanced_drawer).withIcon(GoogleMaterial.Icon.gmd_adb).withIdentifier(5),
                        new SectionDrawerItem().withName(R.string.drawer_item_section_header),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_share).withIcon(FontAwesome.Icon.faw_share_alt),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_send).withIcon(FontAwesome.Icon.faw_paper_plane).withTag("Bullhorn")
                ) // add the items we want to use with our Drawer
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

                        if (drawerItem instanceof Nameable) {
                            toolbar.setTitle(((Nameable) drawerItem).getName().getText(HomeActivity.this));

                        } //ito ang inalis ko dapat ibalik



                        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                            @Override
                            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                                drawerLayout.closeDrawers();

                                if (item.getItemId()== R.id.nav_item_sent) {
                                    FragmentTransaction fragmentTransaction= FM.beginTransaction();
                                    fragmentTransaction.replace(R.id.containerView, new SentFragment()).commit();
                                }

                                if (item.getItemId()==R.id.nav_item_inbox)
                                {
                                    FragmentTransaction fragmentTransaction1=FM.beginTransaction();
                                    fragmentTransaction1.replace(R.id.containerView,new TabFragment()).commit();

                                }
                                return false;
                            }
                        });
//                        if (drawerItem.getIdentifier() == 2) {
//                           // startSupportActionMode(new ActionBarCallBack());
//                        //    findViewById(R.id.action_mode_bar).setBackgroundColor(UIUtils.getThemeColorFromAttrOrRes(CrossfadeDrawerLayoutActvitiy.this, R.attr.colorPrimary, R.color.material_drawer_primary));
//                            // Handle the camera action
//                            Toast.makeText(CrossfadeDrawerLayoutActvitiy.this, ((Nameable) drawerItem).getName().getText(CrossfadeDrawerLayoutActvitiy.this), Toast.LENGTH_SHORT).show();
//                            HomeFragment cameraFragment = new HomeFragment();
//                            FragmentManager manager = getSupportFragmentManager();
//                            manager.beginTransaction().replace(R.id.frame_container, cameraFragment, cameraFragment.getTag()).commit();
//                            }
//                        else if (drawerItem.getIdentifier() == 3) {
//                            // startSupportActionMode(new ActionBarCallBack());
//                            //    findViewById(R.id.action_mode_bar).setBackgroundColor(UIUtils.getThemeColorFromAttrOrRes(CrossfadeDrawerLayoutActvitiy.this, R.attr.colorPrimary, R.color.material_drawer_primary));
//                            // Handle the camera action
//                            ChatFragment cameraFragment = new ChatFragment();
//                            FragmentManager manager = getSupportFragmentManager();
//                            manager.beginTransaction().replace(R.id.frame_container, cameraFragment, cameraFragment.getTag()).commit();
//
//                        }
//                        else if (drawerItem.getIdentifier() == 4) {
//                            AboutFragment cameraFragment = new AboutFragment();
//                            FragmentManager manager = getSupportFragmentManager();
//                            manager.beginTransaction().replace(R.id.frame_container, cameraFragment, cameraFragment.getTag()).commit();
//                        }
                        return false;
                    }
                })
                .addStickyDrawerItems(
                        new SecondaryDrawerItem().withName(R.string.drawer_item_settings).withIcon(FontAwesome.Icon.faw_cogs).withIdentifier(10),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_about_us).withIcon(FontAwesome.Icon.faw_info)
                )
                .withSavedInstance(savedInstanceState)
                .withShowDrawerOnFirstLaunch(true)
                .build();

        //get the CrossfadeDrawerLayout which will be used as alternative DrawerLayout for the Drawer
        //the CrossfadeDrawerLayout library can be found here: https://github.com/mikepenz/CrossfadeDrawerLayout
        crossfadeDrawerLayout = (CrossfadeDrawerLayout) result.getDrawerLayout();

        //define maxDrawerWidth
        crossfadeDrawerLayout.setMaxWidthPx(DrawerUIUtils.getOptimalDrawerWidth(this));
        //add second view (which is the miniDrawer)
        final MiniDrawer miniResult = result.getMiniDrawer();
        //build the view for the MiniDrawer
        View view = miniResult.build(this);
        //set the background of the MiniDrawer as this would be transparent
        view.setBackgroundColor(UIUtils.getThemeColorFromAttrOrRes(this, com.mikepenz.materialdrawer.R.attr.material_drawer_background, com.mikepenz.materialdrawer.R.color.material_drawer_background));
        //we do not have the MiniDrawer view during CrossfadeDrawerLayout creation so we will add it here
        crossfadeDrawerLayout.getSmallView().addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        //define the crossfader to be used with the miniDrawer. This is required to be able to automatically toggle open / close
        miniResult.withCrossFader(new ICrossfader() {
            @Override
            public void crossfade() {
                boolean isFaded = isCrossfaded();
                crossfadeDrawerLayout.crossfade(400);
                //only close the drawer if we were already faded and want to close it now
                if (isFaded) {
                    //result.getDrawerLayout().closeDrawer(GravityCompat.START); ito ang inalis ko dapat ibalik
                }
            }
            @Override
            public boolean isCrossfaded() {
                return crossfadeDrawerLayout.isCrossfaded();
            }
        });

    }





    private void setupTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText("CHAT");
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_chat, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText("ONLINE");
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_online, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabThree.setText("CONTACT");
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_contacts, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabThree);
    }
    private void setupViewPager(ViewPager viewPager) {
        HomeActivity.ViewPagerAdapter adapter = new HomeActivity.ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new OneFragment(), "CHAT");
        adapter.addFrag(new TwoFragment(), "ONLINE");
        adapter.addFrag(new ThreeFragment(), "CONTACT");
        viewPager.setAdapter(adapter);
    }
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
//            return mFragmentTitleList.get(position);
            return null;
        }
    }
    private void loadBackdrop() {
        final ImageView imageView = (ImageView) findViewById(R.id.backdrop);
        Glide.with(this).load("https://unsplash.it/600/300/?random").centerCrop().into(imageView);
    }

    private void fillFab() {
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floating_action_button);
        fab.setImageDrawable(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_favorite).actionBar().color(Color.WHITE));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //add the values which need to be saved from the drawer to the bundle
        outState = result.saveInstanceState(outState);
        //add the values which need to be saved from the accountHeader to the bundle
        outState = headerResult.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        //handle the back press :D close the drawer first and if the drawer is closed close the activity
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }
    class ActionBarCallBack implements ActionMode.Callback {

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            return false;
        }
        // this method create delete button in toolbar
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(UIUtils.getThemeColorFromAttrOrRes(HomeActivity.this, R.attr.colorPrimaryDark, R.color.material_drawer_primary_dark));
            }

            mode.getMenuInflater().inflate(R.menu.cab, menu);
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(Color.TRANSPARENT);
            }
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }
    }

}