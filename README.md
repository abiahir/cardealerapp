# Car Dealer Presentation Generator

This repo contains a simple CLI tool for building editable PowerPoint files that
summarise vehicle listings from sources like AutoTrader. Data can come from a
JSON payload or be supplied inline through command-line flags with controlled
options for fields such as fuel type and gearbox.

## Requirements

- Python 3.10+
- Dependencies listed in `requirements.txt`

Install them with:

```bash
pip install -r requirements.txt
```

## Usage

1. Start with the provided `sample_vehicle.json` or export your own JSON with
   fields that mirror the keys in that example.

2. Generate a presentation using JSON only:

```bash
python generate_ppt.py --input sample_vehicle.json --output listing.pptx
```

3. Override any field via CLI flags (helpful for drop-down style choices):

```bash
python generate_ppt.py \
  --input sample_vehicle.json \
  --fuel-type Petrol \
  --gearbox Manual \
  --engine-size "1.6 L" \
  --owners "1" \
  --specs "Bluetooth" "Parking Sensors" \
  --output petrol-manual.pptx
```

4. To build a slide without JSON, simply omit `--input` and supply details via
   flags:

```bash
python generate_ppt.py \
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

All text in the resulting PPTX remains editable in PowerPoint or Google Slides,
so you can make manual tweaks after generation. The categorical flags
(`--fuel-type`, `--gearbox`, `--ulez`) enforce the same choices you would expect
from dropdowns when filling out the data by hand.
