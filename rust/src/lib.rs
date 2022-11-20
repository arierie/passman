use rand::Rng;
use std::ffi::CString;
use std::os::raw::c_char;

const CHARSET: &[u8] = b"ABCDEFGHIJKLMNOPQRSTUVWXYZ\
abcdefghijklmnopqrstuvwxyz\
0123456789)(*&^%$#@!";

const LEN: i32 = 25;

#[no_mangle]
pub extern "C" fn generate_password() -> *mut c_char {
    let mut rng = rand::thread_rng();

    let password: String = (0..LEN)
        .map(|_| {
            let idx = rng.gen_range(0..CHARSET.len());

            CHARSET[idx] as char
        })
        .collect();

    CString::new(password).unwrap().into_raw()
}

#[cfg(target_os = "android")]
#[allow(non_snake_case)]
pub mod android {
    extern crate jni;

    use self::jni::objects::{JClass};
    use self::jni::sys::jstring;
    use self::jni::JNIEnv;
    use super::*;

    #[no_mangle]
    pub unsafe extern "C" fn Java_dev_arie_passman_PasswordGenerator_generate(
        env: JNIEnv,
        _: JClass
    ) -> jstring {
        let password = generate_password();
        let password_ptr = CString::from_raw(password);
        let output = env
            .new_string(password_ptr.to_str().unwrap())
            .expect("Couldn't create java string!");

        output.into_inner()
    }
}
