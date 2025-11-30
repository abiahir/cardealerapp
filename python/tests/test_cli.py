"""Integration tests for the CLI entry point."""

from __future__ import annotations

import json
import os
import subprocess
import sys
from pathlib import Path


def test_cli_accepts_lowercase_enum_values(tmp_path):
    """Ensure `python -m cardealer` works with lowercase categorical values."""
    payload = {
        "title": "Integration Test",
        "price": "£1,234",
        "registration": "AB12 XYZ",
        "year": "2024",
        "gearbox": "manual",
        "engine_size": "1.5 L",
        "fuel_type": "diesel",
        "mileage": "1,000",
        "ulez": "no",
        "mot_expiry": "2030-01-01",
        "owners": "1",
        "specs": ["Test Feature"],
    }
    json_path = tmp_path / "vehicle.json"
    json_path.write_text(json.dumps(payload))
    output_path = tmp_path / "vehicle.pptx"

    env = os.environ.copy()
    src_dir = Path(__file__).resolve().parents[1] / "src"
    env["PYTHONPATH"] = str(src_dir)

    subprocess.run(
        [
            sys.executable,
            "-m",
            "cardealer",
            "--input",
            str(json_path),
            "--output",
            str(output_path),
        ],
        check=True,
        env=env,
    )

    assert output_path.exists(), "CLI did not create the PPTX output file"
