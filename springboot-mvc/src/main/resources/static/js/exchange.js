document.getElementById('amount').addEventListener('input', function() {
    const amountInput = document.getElementById('amount');
    const submitButton = document.querySelector('button[type="submit"]');
    if (parseFloat(amountInput.value) < 0) {
        submitButton.disabled = true;
        document.getElementById('amount-error').style.display = 'block';
    } else {
        submitButton.disabled = false;
        document.getElementById('amount-error').style.display = 'none';
    }
});
