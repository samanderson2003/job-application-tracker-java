// Initialize Sortable.js for drag-and-drop
document.addEventListener('DOMContentLoaded', function() {
    const kanbanColumns = document.querySelectorAll('.kanban-body');
    
    kanbanColumns.forEach(column => {
        new Sortable(column, {
            group: 'jobs',
            animation: 150,
            ghostClass: 'sortable-ghost',
            dragClass: 'sortable-drag',
            onEnd: function(evt) {
                const jobId = evt.item.getAttribute('data-job-id');
                const newStatus = evt.to.getAttribute('data-status');
                
                // Update job status via AJAX
                updateJobStatus(jobId, newStatus);
            }
        });
    });
});

// Update job status
function updateJobStatus(jobId, newStatus) {
    fetch(`/api/jobs/${jobId}/status`, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ status: newStatus })
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Failed to update job status');
        }
        return response.json();
    })
    .then(data => {
        console.log('Job status updated successfully');
        showToast('Job status updated!', 'success');
        // Optionally reload to update statistics
        setTimeout(() => location.reload(), 1000);
    })
    .catch(error => {
        console.error('Error:', error);
        showToast('Failed to update job status', 'error');
        // Reload page to revert the drag
        location.reload();
    });
}

// Save new job
function saveJob() {
    const form = document.getElementById('addJobForm');
    const formData = new FormData(form);
    
    const jobData = {
        companyName: formData.get('companyName'),
        position: formData.get('position'),
        location: formData.get('location'),
        jobType: formData.get('jobType'),
        salaryRange: formData.get('salaryRange'),
        status: formData.get('status'),
        jobUrl: formData.get('jobUrl'),
        deadline: formData.get('deadline'),
        jobDescription: formData.get('jobDescription'),
        notes: formData.get('notes')
    };
    
    fetch('/api/jobs', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(jobData)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Failed to create job');
        }
        return response.json();
    })
    .then(data => {
        console.log('Job created successfully');
        showToast('Job added successfully!', 'success');
        // Close modal
        const modal = bootstrap.Modal.getInstance(document.getElementById('addJobModal'));
        modal.hide();
        // Reset form
        form.reset();
        // Reload page to show new job
        setTimeout(() => location.reload(), 500);
    })
    .catch(error => {
        console.error('Error:', error);
        showToast('Failed to create job', 'error');
    });
}

// Edit job
function editJob(button) {
    const jobId = button.getAttribute('data-job-id');
    
    fetch(`/api/jobs/${jobId}`)
        .then(response => response.json())
        .then(job => {
            // Populate form with job data
            document.querySelector('#addJobForm [name="companyName"]').value = job.companyName || '';
            document.querySelector('#addJobForm [name="position"]').value = job.position || '';
            document.querySelector('#addJobForm [name="location"]').value = job.location || '';
            document.querySelector('#addJobForm [name="jobType"]').value = job.jobType || '';
            document.querySelector('#addJobForm [name="salaryRange"]').value = job.salaryRange || '';
            document.querySelector('#addJobForm [name="status"]').value = job.status || '';
            document.querySelector('#addJobForm [name="jobUrl"]').value = job.jobUrl || '';
            document.querySelector('#addJobForm [name="deadline"]').value = job.deadline || '';
            document.querySelector('#addJobForm [name="jobDescription"]').value = job.jobDescription || '';
            document.querySelector('#addJobForm [name="notes"]').value = job.notes || '';
            
            // Change modal title and button
            document.querySelector('#addJobModal .modal-title').innerHTML = '<i class="fas fa-edit me-2"></i>Edit Job Application';
            document.querySelector('#addJobModal .btn-primary').setAttribute('onclick', `updateJob(${jobId})`);
            document.querySelector('#addJobModal .btn-primary').textContent = 'Update Job';
            
            // Show modal
            const modal = new bootstrap.Modal(document.getElementById('addJobModal'));
            modal.show();
        })
        .catch(error => {
            console.error('Error:', error);
            showToast('Failed to load job details', 'error');
        });
}

// Update job
function updateJob(jobId) {
    const form = document.getElementById('addJobForm');
    const formData = new FormData(form);
    
    const jobData = {
        companyName: formData.get('companyName'),
        position: formData.get('position'),
        location: formData.get('location'),
        jobType: formData.get('jobType'),
        salaryRange: formData.get('salaryRange'),
        status: formData.get('status'),
        jobUrl: formData.get('jobUrl'),
        deadline: formData.get('deadline'),
        jobDescription: formData.get('jobDescription'),
        notes: formData.get('notes')
    };
    
    fetch(`/api/jobs/${jobId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(jobData)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Failed to update job');
        }
        return response.json();
    })
    .then(data => {
        console.log('Job updated successfully');
        showToast('Job updated successfully!', 'success');
        // Close modal
        const modal = bootstrap.Modal.getInstance(document.getElementById('addJobModal'));
        modal.hide();
        // Reset form
        form.reset();
        // Reset modal to add mode
        resetModal();
        // Reload page
        setTimeout(() => location.reload(), 500);
    })
    .catch(error => {
        console.error('Error:', error);
        showToast('Failed to update job', 'error');
    });
}

// Delete job
function deleteJob(button) {
    if (!confirm('Are you sure you want to delete this job application?')) {
        return;
    }
    
    const jobId = button.getAttribute('data-job-id');
    
    fetch(`/api/jobs/${jobId}`, {
        method: 'DELETE'
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Failed to delete job');
        }
        console.log('Job deleted successfully');
        showToast('Job deleted successfully!', 'success');
        // Remove the job card from DOM
        button.closest('.job-card').remove();
        // Reload page to update statistics
        setTimeout(() => location.reload(), 500);
    })
    .catch(error => {
        console.error('Error:', error);
        showToast('Failed to delete job', 'error');
    });
}

// Reset modal to add mode
function resetModal() {
    document.querySelector('#addJobModal .modal-title').innerHTML = '<i class="fas fa-plus-circle me-2"></i>Add New Job Application';
    document.querySelector('#addJobModal .btn-primary').setAttribute('onclick', 'saveJob()');
    document.querySelector('#addJobModal .btn-primary').textContent = 'Save Job';
}

// Show toast notification
function showToast(message, type = 'info') {
    // Create toast element
    const toastContainer = document.createElement('div');
    toastContainer.style.cssText = 'position: fixed; top: 20px; right: 20px; z-index: 9999;';
    
    const bgColor = type === 'success' ? '#48bb78' : type === 'error' ? '#f56565' : '#4299e1';
    
    toastContainer.innerHTML = `
        <div class="alert alert-dismissible fade show" role="alert" style="background: ${bgColor}; color: white; min-width: 250px;">
            ${message}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
    `;
    
    document.body.appendChild(toastContainer);
    
    // Auto remove after 3 seconds
    setTimeout(() => {
        toastContainer.remove();
    }, 3000);
}

// Reset form when modal is closed
document.getElementById('addJobModal')?.addEventListener('hidden.bs.modal', function () {
    document.getElementById('addJobForm').reset();
    resetModal();
});
