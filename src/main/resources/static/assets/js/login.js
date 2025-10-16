(function () {
    // Guard if jQuery is not found
    if (typeof window.jQuery === "undefined") {
        console.warn("jQuery not found. ");
        return;
    }

    const emailRegex =
        /^[^\s@]+@[^\s@]+\.[^\s@]{2,}$/i;
    const hasUpper = /[A-Z]/;
    const hasLower = /[a-z]/;
    const hasDigit = /\d/;

    function setError($input, $err, msg) {
        $input.attr("aria-invalid", "true");
        $err.text(msg);
    }
    function clearError($input, $err) {
        $input.attr("aria-invalid", "false");
        $err.text("");
    }
    function trimValue($input) {
        const v = $input.val();
        if (typeof v === "string") $input.val(v.trim());
    }

    function validateEmail($input, $err) {
        trimValue($input);
        const v = String($input.val() || "");
        if (!v) {
            setError($input, $err, "Email is required.");
            return false;
        }
        if (v.length < 5 || v.length > 254) {
            setError($input, $err, "Email length must be between 5 and 254 characters.");
            return false;
        }
        if (!emailRegex.test(v)) {
            setError($input, $err, "Enter a valid email address.");
            return false;
        }
        clearError($input, $err);
        return true;
    }

    function validatePassword($input, $err) {
        // Note: Server is the source of truth; enforce client hints only.
        const v = String($input.val() || "");
        if (!v) {
            setError($input, $err, "Password is required.");
            return false;
        }
        if (v.length < 8) {
            setError($input, $err, "Password must be at least 8 characters.");
            return false;
        }
        if (v.length > 72) {
            setError($input, $err, "Password must be 72 characters or fewer.");
            return false;
        }
        if (!hasUpper.test(v) || !hasLower.test(v) || !hasDigit.test(v)) {
            setError($input, $err, "Use upper, lower, and a number for stronger security.");
            return false;
        }
        clearError($input, $err);
        return true;
    }

    $(function () {
        const $form = $("#loginForm");
        const $username = $("#username");
        const $password = $("#password");
        const $uErr = $("#usernameError");
        const $pErr = $("#passwordError");
        const $submit = $("#submitBtn");

        // Realtime validation feedback
        $username.on("blur input", function () {
            validateEmail($username, $uErr);
        });
        $password.on("blur input", function () {
            validatePassword($password, $pErr);
        });

        // Prevent double submit and validate on submit
        $form.on("submit", function (e) {
            // Force autocomplete off again (some browsers ignore)
            $form.attr("autocomplete", "off");
            $username.attr("autocomplete", "off");
            $password.attr("autocomplete", "off");

            const okEmail = validateEmail($username, $uErr);
            const okPass = validatePassword($password, $pErr);

            if (!okEmail || !okPass) {
                e.preventDefault();
                // Focus first invalid field
                if (!okEmail) $username.trigger("focus");
                else $password.trigger("focus");
                return false;
            }

            // Prevent accidental resubmits
            $submit.prop("disabled", true).attr("aria-busy", "true").text("Signing in...");
            // Let the request proceed
            return true;
        });
    });
})();