package radioestekhdam.com.espars.utilities.cab;

import android.view.Menu;
import android.view.MenuItem;

public interface CabCallback {
    boolean onCreateCab(ContextualActionBar cab, Menu menu);
    boolean onCabItemClicked(MenuItem item);
    boolean onDestroyCab(ContextualActionBar cab);
}
