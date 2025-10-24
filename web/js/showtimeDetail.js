/**
 * Submits the pagination form with the target page number.
 * @param {number} pageNumber - The page number to navigate to.
 */
function goToPage(pageNumber) {
    const pageInput = document.getElementById('pageInput'); // Changed ID for clarity
    const paginationForm = document.getElementById('paginationForm');
    if (pageInput && paginationForm) {
        pageInput.value = pageNumber;
        paginationForm.submit();
    } else {
        console.error("Could not find page input or pagination form.");
    }
}

// Add event listener for clicking on table rows to submit detail forms
document.addEventListener('DOMContentLoaded', () => {
    const tableRows = document.querySelectorAll('tbody tr[data-form-id]');
    tableRows.forEach(row => {
        row.addEventListener('click', () => {
            const formId = row.getAttribute('data-form-id');
            const form = document.getElementById(formId);
            if (form) {
                form.submit();
            } else {
                console.error(`Could not find detail form with ID: ${formId}`);
            }
        });
    });

   
    const paginationLinks = document.querySelectorAll('.pagination a[data-page]');
    paginationLinks.forEach(link => {
        link.addEventListener('click', (event) => {
            event.preventDefault(); // Prevent default link behavior
            const page = link.getAttribute('data-page');
            if (page && !link.classList.contains('disabled') && !link.classList.contains('active')) {
                goToPage(parseInt(page, 10));
            }
        });
    });
});