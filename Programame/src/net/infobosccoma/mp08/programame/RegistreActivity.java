package net.infobosccoma.mp08.programame;

import java.util.List;

import net.infobosccoma.mp08.programame.model.Usuari;
import net.infobosccoma.mp08.programame.utils.BitmapUtils;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class RegistreActivity extends ActionBarActivity {

	static final int FOTO_CODE = 100;
	private EditText edNom;
	private EditText edCognoms;
	private ImageView imgLlibretaDireccions;
	private Usuari usuariVisita;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registre);

		initComponents();
		usuariVisita = new Usuari();
	}

	@Override
	protected void onResume() {
		super.onResume();
		// En cas de l'aplicació hagi perdut el focus, recargar la imatge
		// capturada
		if (usuariVisita.getFotografia() != null) {
			imgLlibretaDireccions.setImageBitmap(usuariVisita.getFotografia());
		}
	}

	private void initComponents() {
		edNom = (EditText) findViewById(R.id.etNom);
		edCognoms = (EditText) findViewById(R.id.etCognoms);
		imgLlibretaDireccions = (ImageView) findViewById(R.id.imgLlibretaDireccions);
		// Orientació vertical
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.registre, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Gestionar el clic del botó al finalitzar el registre
	 * @param view
	 */
	public void iniciarMenuIconic(View view) {
		String nom = edNom.getText().toString();
		String cognoms = edCognoms.getText().toString();

		if (!nom.trim().equals("")
				&& !cognoms.trim().equals("")
				&& !getResources()
						.getDrawable(R.drawable.default_img)
						.getConstantState()
						.equals(imgLlibretaDireccions.getDrawable()
								.getConstantState())) {

			startActivity(new Intent(this, MenuIconic.class));
		} else {
			Toast.makeText(this,
					"Cal omplir el formulari per registrar la vostre visita",
					Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * Click a la imatge: Realitzar fotografia
	 * 
	 * @param view
	 */
	public void ferFotografia(View view) {
		// Comprovar si es pot obrir la càmera en el dispositiu
		if (isIntentAvailable(this, MediaStore.ACTION_IMAGE_CAPTURE)) {
			Intent fotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			
			// iniciar l'intent
			startActivityForResult(fotoIntent, FOTO_CODE);
		} else {
			Toast.makeText(this,
					"No hi ha cap aplicació per realitzar fotografies",
					Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == FOTO_CODE) {
			if (resultCode == RESULT_OK) {
				// mostrar el fitxer que ha desat l'app de captura
				Bitmap imgUsuari = (Bitmap) data.getExtras().get("data");
				imgUsuari = BitmapUtils.getRoundCorneredBitmapFrom(imgUsuari, 15);
				imgUsuari = BitmapUtils.fixOrientation(imgUsuari);
				imgLlibretaDireccions.setImageBitmap(imgUsuari);
				usuariVisita.setFotografia(imgUsuari);
			}
		}
	}

	/**
	 * Mètode que comprova si hi ha una aplicació per a captura de fotos
	 * 
	 * @param context
	 * @param action
	 * @return true si existeix, false en cas contrari
	 */
	public static boolean isIntentAvailable(Context context, String action) {
		final PackageManager packageManager = context.getPackageManager();
		final Intent intent = new Intent(action);
		List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
				PackageManager.MATCH_DEFAULT_ONLY);
		return list.size() > 0;
	}
	
	

}
