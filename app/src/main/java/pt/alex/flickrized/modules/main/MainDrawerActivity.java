package pt.alex.flickrized.modules.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.googlecode.flickrjandroid.FlickrException;
import com.googlecode.flickrjandroid.people.User;
import com.googlecode.flickrjandroid.photos.Photo;
import com.googlecode.flickrjandroid.photos.PhotoList;
import com.googlecode.flickrjandroid.photos.Size;

import java.util.List;

import pt.alex.flickrized.R;
import pt.alex.flickrized.data.DataHelper;
import pt.alex.flickrized.modules.details.ActivityDetails;
import pt.alex.flickrized.modules.main.grid.WallReciclerViewAdapter;
import pt.alex.flickrized.network.Dispatcher;

public class MainDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private int page = 1;

    RecyclerView cardList;
    StaggeredGridLayoutManager stagLayoutManager;
    WallReciclerViewAdapter wallAdapter;

    private String searchUserId;


    private ViewGroup progressBar;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /** para aumentar performace
         * deveria colocar cache nos pedidos
         */
        if (savedInstanceState == null) {
            setContentView(R.layout.activity_main_drawer);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            // init reclicler View
            cardList = (RecyclerView) findViewById(R.id.flickr_card_list);
            cardList.setHasFixedSize(true);


            progressBar = (ViewGroup) findViewById(R.id.progressbar_container);

            // init wall
            // o numero de colunas está defindo nos resources nas pastas de accordo com os tamanhos minimo disponivel sw
            stagLayoutManager = new StaggeredGridLayoutManager(getResources().getInteger(R.integer.number_of_rows), StaggeredGridLayoutManager.VERTICAL);

            cardList.setLayoutManager(stagLayoutManager);

            wallAdapter = new WallReciclerViewAdapter(this, cardClickListner);
            cardList.setAdapter(wallAdapter);
            // continua a carregar casso chegue lá baixo
            wallAdapter.setEndlessListener(scroolListener);

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);

            // start Load of flickr data
            // TODO testing
            loadUserData();
        }
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    /***
     * Endless scrool listener, para passar para o adapter
     * continua a carregar as imagens
     */
    private WallReciclerViewAdapter.ContinuumScrollListener scroolListener = new WallReciclerViewAdapter.ContinuumScrollListener() {
        @Override
        public boolean onContinueLoad(int pos) {
//            increase page number
            page++;
            loadGetPublicPhoto(searchUserId, page);
            return true;
        }
    };


    /***
     * Listener com logica para o click nos cartoes
     */
    private WallReciclerViewAdapter.ItemClickListner cardClickListner = new WallReciclerViewAdapter.ItemClickListner() {
        @Override
        public void onItemClick(int position) {
            goToDetailAcitivty(position);
        }
    };

    private void goToDetailAcitivty(int pressedIndex) {
        Intent i = new Intent(this, ActivityDetails.class);
        i.putExtra("POS", pressedIndex);
        //TODO: passar o indice do cartão carregado para contextualizar
        startActivity(i);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void updatePhotoList(int i) {
        wallAdapter.setPhotos(DataHelper.of().getPhotos());
        wallAdapter.notifyDataSetChanged();
//                wallAdapter.notifyItemChanged(i);
    }

    /***********************************/


    /**
     * optei por nao meter asyncs
     * Se houver tempo coloco de forma mais generica para centralizar o handler
     */
    public void loadUserData() {
        showProgress();
        Dispatcher.getUserByUserName(new Dispatcher.Callback<User>() {
            @Override
            public void onResponse(User response) {
                hideProgress();
                searchUserId = response.getId();
                loadGetPublicPhoto(response.getId(), page);

            }

            @Override
            public void onError(FlickrException response) {
                //TODO:
                hideProgress();
            }
        });
    }

    public void loadGetPublicPhoto(final String searchUser, final int pageNumber) {
        showProgress();
        Dispatcher.getPublicPhotos(new Dispatcher.Callback<PhotoList>() {
            @Override
            public void onResponse(PhotoList response) {
                hideProgress();
                /**
                 * pelo sim pelo nao
                 */
                if (response != null && response.size() == 0) {
                    // TODO : adicionar uma msg de erro
                    // para desenrascar meter um toast
                } else {
                    // update list
                    DataHelper.of().getPhotos().addAll(response);
                    updatePhotoList(0);
                }


            }

            @Override
            public void onError(FlickrException response) {
                //TODO:
                hideProgress();
            }
        }, searchUser, 20, pageNumber); // tocar por variaveis

    }


    /**
     * todo : refactor
     *
     * @param p
     */

    public void getPhotoSize(final Photo p) {
        Dispatcher.getPhotoSizes(new Dispatcher.Callback<List<Size>>() {
            @Override
            public void onResponse(List<Size> response) {
                p.setSizes(response);
                updatePhotoList(0);

            }

            @Override
            public void onError(FlickrException response) {

            }
        }, p.getId());
    }


    private void showProgress() {
        if(progressBar.getVisibility() == View.GONE){
            progressBar.setVisibility(View.VISIBLE);
        }
    }
    private void hideProgress(){
        progressBar.setVisibility(View.GONE);
    }


}
