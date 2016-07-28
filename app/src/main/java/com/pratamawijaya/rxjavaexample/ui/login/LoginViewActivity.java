package com.pratamawijaya.rxjavaexample.ui.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.pratamawijaya.rxjavaexample.R;
import com.pratamawijaya.rxjavaexample.ui.home.HomeViewActivity;
import java.util.regex.Pattern;
import rx.Observable;
import rx.subscriptions.CompositeSubscription;

public class LoginViewActivity extends AppCompatActivity {

  @BindView(R.id.input_username) EditText inputUsername;
  @BindView(R.id.input_password) EditText inputPassword;
  @BindView(R.id.btn_login) Button btnLogin;

  private CompositeSubscription compositeSubscription = new CompositeSubscription();

  final Pattern emailPattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
      + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login_view);
    ButterKnife.bind(this);

    compositeSubscription.add(
        checkUsernameValid().map(usernameValid -> usernameValid ? Color.BLACK : Color.RED)
            .subscribe(color -> inputUsername.setTextColor(color)));

    compositeSubscription.add(
        checkPasswordValid().map(passwordValid -> passwordValid ? Color.BLACK : Color.RED)
            .subscribe(color -> inputPassword.setTextColor(color)));

    compositeSubscription.add(
        checkFormValid().subscribe(buttonEnabled -> btnLogin.setEnabled(buttonEnabled)));
  }

  /**
   * check username is valid
   *
   * @return Boolean
   */
  private Observable<Boolean> checkUsernameValid() {
    return RxTextView.textChanges(inputUsername).map(txt -> emailPattern.matcher(txt).matches());
  }

  /**
   * check password is valid
   *
   * @return Boolean
   */
  private Observable<Boolean> checkPasswordValid() {
    return RxTextView.textChanges(inputPassword).map(txt -> txt.length() > 4);
  }

  /**
   * check all form valid
   *
   * @return Boolean
   */
  private Observable<Boolean> checkFormValid() {
    return Observable.combineLatest(checkUsernameValid(), checkPasswordValid(), (a, b) -> a && b);
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    compositeSubscription.unsubscribe();
  }

  @OnClick(R.id.btn_login) void login() {
    final String username = inputUsername.getText().toString();
    final String password = inputPassword.getText().toString();

    if (username.equals("tama@fanboy.id") && password.equals("jogja")) {
      startActivity(new Intent(this, HomeViewActivity.class));
      finish();
    }
  }
}
