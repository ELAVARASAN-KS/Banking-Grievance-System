// admin_dashboard.js
const API = 'http://localhost:8080/api';

let selectedComplaintId = null;

function ensureAdmin() {
  const auth = JSON.parse(localStorage.getItem('auth'));
  if (!auth || auth.role !== 'admin') {
    window.location.href = 'login.html';
  }
}

// Logout
document.addEventListener('DOMContentLoaded', () => {
  ensureAdmin();
  document.getElementById('logoutBtn').addEventListener('click', () => {
    localStorage.removeItem('auth');
    window.location.href = 'login.html';
  });

  document.getElementById('filterBtn').addEventListener('click', () => loadComplaints());
  document.getElementById('resetBtn').addEventListener('click', () => {
    document.getElementById('categoryFilter').value = '';
    loadComplaints();
  });

  document.getElementById('assignCancel').addEventListener('click', () => {
    document.getElementById('assignModal').classList.add('hidden');
  });

  document.getElementById('assignConfirm').addEventListener('click', async () => {
    const staffId = document.getElementById('staffSelect').value;
    if (!staffId) { alert('Select staff'); return; }
    const res = await fetch(`${API}/complaints/${selectedComplaintId}/assign`, {
      method: 'PUT', headers: {'Content-Type':'application/json'}, body: JSON.stringify({staffId: parseInt(staffId)})
    });
    if (res.ok) {
      alert('Assigned');
      document.getElementById('assignModal').classList.add('hidden');
      loadComplaints();
    } else { alert('Failed to assign'); }
  });

  loadStats();
  loadStaffOptions();
  loadComplaints();
});

async function loadStats() {
  // API didn't include statistics endpoint implementation above — try /complaints/statistics if exists
  try {
    const res = await fetch(`${API}/complaints/statistics`);
    if (!res.ok) return;
    const body = await res.json();
    if (body.statistics) {
      document.getElementById('statTotal').innerText = body.statistics.totalComplaints;
      document.getElementById('statResolved').innerText = body.statistics.resolved;
      document.getElementById('statInprogress').innerText = body.statistics.inProgress;
      document.getElementById('statUnresolved').innerText = body.statistics.unresolved;
    }
  } catch (e) { /* ignore */ }
}

async function loadStaffOptions() {
  const res = await fetch(`${API}/staff`);
  if (!res.ok) return;
  const list = await res.json();
  const sel = document.getElementById('staffSelect');
  sel.innerHTML = '<option value="">-- select staff --</option>';
  list.forEach(s => {
    const opt = document.createElement('option');
    opt.value = s.staffId;
    opt.textContent = `${s.fullName} (${s.department})`;
    sel.appendChild(opt);
  });
}

async function loadComplaints() {
  const res = await fetch(`${API}/complaints`);
  if (!res.ok) { console.error('Failed to load complaints'); return; }
  const list = await res.json();
  const tbody = document.getElementById('complaintTable');
  tbody.innerHTML = '';
  list.forEach(c => {
    const tr = document.createElement('tr');
    tr.className = 'border-b border-gray-800';
    tr.innerHTML = `
      <td class="py-3">${c.complaintNumber}</td>
      <td class="py-3">${escapeHtml(c.subject)}</td>
      <td class="py-3">${c.category}</td>
      <td class="py-3">${c.userId}</td>
      <td class="py-3">${c.dateRaised ?? ''}</td>
      <td class="py-3">${c.status}</td>
      <td class="py-3">${c.staffId ?? '—'}</td>
      <td class="py-3"><button class="px-3 py-1 bg-blue-600 rounded" onclick="openAssign(${c.complaintId})">Assign</button>
          <button class="px-3 py-1 bg-gray-700 rounded ml-2" onclick="viewComplaint('${c.complaintNumber}')">View</button></td>
    `;
    tbody.appendChild(tr);
  });
}

function openAssign(id) {
  selectedComplaintId = id;
  document.getElementById('assignModal').classList.remove('hidden');
}

function viewComplaint(number) {
  window.location.href = `view_complaint.html?number=${encodeURIComponent(number)}`;
}

// simple escape
function escapeHtml(str){ return (str||'').replace(/&/g,'&amp;').replace(/</g,'&lt;').replace(/>/g,'&gt;'); }
