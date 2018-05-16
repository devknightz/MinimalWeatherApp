package dagger.android;

import static java.lang.annotation.ElementType.METHOD;

import androidx.fragment.app.Fragment;
import dagger.MapKey;
import dagger.internal.Beta;
import java.lang.annotation.Target;

/** {@link MapKey} annotation to key bindings by a type of a {@link Fragment}. */
@Beta
@MapKey
@Target(METHOD)
public @interface FragmentKey {
  Class<? extends Fragment> value();
}
