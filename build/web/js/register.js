document.addEventListener('DOMContentLoaded', function() {
    
    const passwordInput = document.querySelector('input[name="password"]');
    const confirmPasswordInput = document.querySelector('input[name="confirmPassword"]');
    const confirmPasswordError = document.getElementById('confirmPasswordError');
    const showPasswordToggle = document.getElementById('showPasswordToggle');

    
    function validateConfirmPassword() {
        const password = passwordInput.value;
        const confirmPassword = confirmPasswordInput.value;
        
        if (confirmPassword.length > 0 || password.length > 0) {
            if (password !== confirmPassword) {
                confirmPasswordError.innerHTML = 'The passwords you entered do not match.';
                return false;
            }
        }
        
        confirmPasswordError.innerHTML = ''; 
        return true; 
    }

    
    passwordInput.addEventListener('blur', validateConfirmPassword);
    confirmPasswordInput.addEventListener('blur', validateConfirmPassword);
    showPasswordToggle.addEventListener('change', function() {
        const isChecked = this.checked; 
        passwordInput.type = isChecked ? 'text' : 'password';
        confirmPasswordInput.type = isChecked ? 'text' : 'password';
    });
    
 
    document.getElementById('registerForm').addEventListener('submit', function(event) {
    
        const isConfirmValid = validateConfirmPassword();

        if (!isConfirmValid) {
            event.preventDefault(); 
        }
    });
});