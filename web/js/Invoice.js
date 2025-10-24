/**
 * Submits the pagination form for invoices with the target page number.
 * @param {number} pageNumber - The page number to navigate to.
 */
function goToInvoicePage(pageNumber) {
    const pageInput = document.getElementById('invoicePageInput'); // Specific ID for invoice page
    const paginationForm = document.getElementById('invoicePaginationForm');
    if (pageInput && paginationForm) {
        pageInput.value = pageNumber;
        paginationForm.submit();
    } else {
        console.error("Could not find invoice page input or pagination form.");
    }
}

// Add event listeners for pagination links
document.addEventListener('DOMContentLoaded', () => {
    const paginationLinks = document.querySelectorAll('.pagination a[data-page]');
    paginationLinks.forEach(link => {
        link.addEventListener('click', (event) => {
            event.preventDefault(); // Prevent default link behavior
            const page = link.getAttribute('data-page');
            // Check if the link is clickable (not disabled or active)
            if (page && !link.classList.contains('disabled') && !link.classList.contains('active')) {
                goToInvoicePage(parseInt(page, 10)); // Call the correct function
            }
        });
    });
});