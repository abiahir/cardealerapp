# Car Dealer Presentation Generator - Python

Python implementation for generating editable PowerPoint presentations from vehicle listing data.

## 📋 Requirements

- Python 3.10 or higher
- Dependencies listed in `requirements.txt`

## 🚀 Quick Start

### Installation

```bash
# Install dependencies
pip install -r requirements.txt

# Or install as a package (recommended)
pip install -e .
```

### Basic Usage

```bash
# Using module execution
python -m cardealer --input data/sample_vehicle.json --output listing.pptx

# Or if installed globally
generate-ppt --input data/sample_vehicle.json --output listing.pptx
```

## 📦 Package Structure

```
python/
├── src/
│   └── cardealer/          # Main package
│       ├── __init__.py     # Package initialization
│       ├── cli.py          # Command-line interface
│       ├── constants.py   # Configuration constants
│       ├── models.py       # Data models (VehicleListing, DealerDetails)
│       └── presentation.py # PowerPoint generation logic
├── tests/                  # Unit tests
├── data/                   # Sample JSON files
├── requirements.txt        # Dependencies
├── setup.py               # Package setup
└── pyproject.toml         # Modern Python configuration
```

## 💻 Usage Examples

### 1. Generate from JSON File

```bash
python -m cardealer --input data/sample_vehicle.json --output listing.pptx
```

### 2. Override Fields from JSON

```bash
python -m cardealer \
  --input data/sample_vehicle.json \
  --fuel-type Petrol \
  --gearbox Manual \
  --engine-size "1.6 L" \
  --owners "1" \
  --specs "Bluetooth" "Parking Sensors" \
  --output petrol-manual.pptx
```

### 3. Create Without JSON (All CLI)

```bash
python -m cardealer \
  --title "Example Hatchback" \
  --price "£8,995" \
  --registration "AB12 CDE" \
  --year "2020 (70 reg)" \
  --fuel-type Hybrid \
  --gearbox Automatic \
  --engine-size "1.5 L" \
  --mileage "12,345" \
  --ulez Yes \
  --mot-expiry "12 Dec 2025" \
  --specs "Apple CarPlay" "Reverse Camera" \
  --dealer-name "Example Cars" \
  --dealer-phone "01234 567890" \
  --dealer-email "sales@example.com" \
  --output hatchback.pptx
```

## 📝 Available Options

### Vehicle Fields

- `--title`: Vehicle display title
- `--price`: Price string (e.g., "£12,000")
- `--registration`: Registration number
- `--year`: Year display value
- `--gearbox`: `Automatic` or `Manual`
- `--engine-size`: Engine size label
- `--fuel-type`: `Petrol`, `Diesel`, `Hybrid`, or `Electric`
- `--mileage`: Mileage display value
- `--ulez`: `Yes`, `No`, or `Unknown`
- `--mot-expiry`: MOT expiry display value
- `--owners`: Number of owners or description
- `--specs`: List of spec bullet points (can specify multiple)

### Dealer Fields

- `--dealer-name`: Dealership name
- `--dealer-phone`: Dealership phone number
- `--dealer-email`: Dealership email address
- `--dealer-website`: Dealership website

## 🧪 Development

### Setup Development Environment

```bash
# Create virtual environment
python -m venv venv
source venv/bin/activate  # On Windows: venv\Scripts\activate

# Install in development mode
pip install -e .

# Install development dependencies
pip install -e ".[dev]"  # If you add dev dependencies
```

### Running Tests

```bash
# Install pytest
pip install pytest

# Run tests
pytest tests/
```

### Code Quality

```bash
# Format code with black
pip install black
black src/

# Lint with pylint
pip install pylint
pylint src/

# Type checking with mypy
pip install mypy
mypy src/
```

## 📄 JSON Input Format

```json
{
  "title": "Land Rover Discovery Sport",
  "price": "£12,000",
  "registration": "KN16 ZVU",
  "year": "2016 (16 reg)",
  "gearbox": "Automatic",
  "engine_size": "2.0 L",
  "fuel_type": "Diesel",
  "mileage": "71,980",
  "ulez": "Yes",
  "mot_expiry": "19 Nov 2026",
  "owners": "2",
  "specs": ["BT", "NAV", "DAB Radio", "Rear Camera", "PX"],
  "dealer": {
    "name": "M&N Cars",
    "phone": "07446 877759",
    "email": "sales@mandncars.co.uk",
    "website": "www.mandncars.uk"
  }
}
```

## 🔧 API Usage

You can also use the package programmatically:

```python
from cardealer import VehicleListing, PresentationBuilder
from pathlib import Path

# Create vehicle listing
vehicle = VehicleListing.from_dict({
    "title": "Ford Kuga",
    "price": "£18,500",
    # ... other fields
})

# Generate presentation
PresentationBuilder.build(vehicle, Path("output.pptx"))
```

## 📚 Dependencies

- `python-pptx==0.6.21`: PowerPoint file generation
- `pillow==10.4.0`: Image processing support

## 🐛 Troubleshooting

### Import Errors

If you get import errors, make sure you're running from the correct directory or have installed the package:

```bash
pip install -e .
```

### Module Not Found

If using `python -m cardealer`, ensure you're in the `python/` directory or have the package installed.

## 📖 See Also

- [Main README](../README.md) for project overview
- [Java Version](../java/README.md) for Java implementation

