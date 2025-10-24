function goToPage(pageNumber) {
    const pageInput = document.getElementById('page');
    const filterForm = document.getElementById('filterForm');
    if (pageInput && filterForm) {
        pageInput.value = pageNumber;
        filterForm.submit();
    } else {
        console.error("Could not find page input or filter form.");
    }
}

// Thêm sự kiện click cho các hàng trong bảng (nếu bạn muốn giữ lại chức năng click)
document.addEventListener('DOMContentLoaded', () => {
    const tableRows = document.querySelectorAll('tbody tr[data-form-id]');
    tableRows.forEach(row => {
        row.addEventListener('click', () => {
            const formId = row.getAttribute('data-form-id');
            const form = document.getElementById(formId);
            if (form) {
                form.submit();
            } else {
                console.error(`Could not find form with ID: ${formId}`);
            }
        });
    });
});