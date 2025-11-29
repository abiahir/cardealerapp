# Git Push Script for Car Dealer App
# Run this script after Git is installed and configured

Write-Host "=== Git Push Script ===" -ForegroundColor Cyan
Write-Host ""

# Check if git is available
try {
    $gitVersion = git --version 2>&1
    Write-Host "✓ Git found: $gitVersion" -ForegroundColor Green
} catch {
    Write-Host "✗ Git not found. Please install Git first." -ForegroundColor Red
    Write-Host "Download from: https://git-scm.com/downloads" -ForegroundColor Yellow
    exit 1
}

Write-Host ""
Write-Host "Step 1: Checking git status..." -ForegroundColor Cyan
git status

Write-Host ""
Write-Host "Step 2: Creating branch (if needed)..." -ForegroundColor Cyan
$currentBranch = git branch --show-current 2>&1
if ($LASTEXITCODE -ne 0 -or $currentBranch -eq "") {
    Write-Host "Creating new branch: feature/complete-restructuring" -ForegroundColor Yellow
    git checkout -b feature/complete-restructuring
} else {
    Write-Host "Current branch: $currentBranch" -ForegroundColor Green
    if ($currentBranch -eq "main" -or $currentBranch -eq "master") {
        Write-Host "Creating feature branch..." -ForegroundColor Yellow
        git checkout -b feature/complete-restructuring
    }
}

Write-Host ""
Write-Host "Step 3: Staging all changes..." -ForegroundColor Cyan
git add .
Write-Host "✓ Files staged" -ForegroundColor Green

Write-Host ""
Write-Host "Step 4: Committing changes..." -ForegroundColor Cyan
$commitMessage = @"
feat: Complete project restructuring and modernization

- Restructure project with separate Python and Java implementations
- Create proper package structures following best practices
- Add comprehensive documentation
- Remove duplicates and unnecessary files
- Fix naming conventions
- Verify all code is error-free

Breaking Changes:
- Python: Usage changed from python generate_ppt.py to python -m cardealer
- Java: Files moved from root to java/ directory

See PULL_REQUEST.md for full details
"@

git commit -m $commitMessage
if ($LASTEXITCODE -eq 0) {
    Write-Host "✓ Changes committed" -ForegroundColor Green
} else {
    Write-Host "✗ Commit failed. Check git status." -ForegroundColor Red
    exit 1
}

Write-Host ""
Write-Host "Step 5: Checking remote..." -ForegroundColor Cyan
$remote = git remote get-url origin 2>&1
if ($LASTEXITCODE -ne 0) {
    Write-Host "✗ No remote configured. Please add remote first:" -ForegroundColor Red
    Write-Host "  git remote add origin <your-repo-url>" -ForegroundColor Yellow
    exit 1
} else {
    Write-Host "✓ Remote: $remote" -ForegroundColor Green
}

Write-Host ""
Write-Host "Step 6: Pushing to remote..." -ForegroundColor Cyan
$branch = git branch --show-current
git push -u origin $branch

if ($LASTEXITCODE -eq 0) {
    Write-Host ""
    Write-Host "✓✓✓ SUCCESS! Changes pushed to remote ✓✓✓" -ForegroundColor Green
    Write-Host ""
    Write-Host "Next steps:" -ForegroundColor Cyan
    Write-Host "1. Go to your repository on GitHub" -ForegroundColor White
    Write-Host "2. Click 'Compare & pull request'" -ForegroundColor White
    Write-Host "3. Copy content from PULL_REQUEST.md into PR description" -ForegroundColor White
    Write-Host "4. Create the pull request" -ForegroundColor White
} else {
    Write-Host "✗ Push failed. Check your remote configuration." -ForegroundColor Red
}

