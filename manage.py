#!/usr/bin/env python
"""Django's command-line utility for administrative tasks."""
import os
import sys

if __name__ == '__main__':
    debug = os.environ.get('DEBUG', None) # Grabs DEBUG off of settings
    settings = 'root.local'
    try:

        if debug is False and debug is not None:
            settings = 'root.production'

        else:
            settings = 'root.local'

    except ModuleNotFoundError:
        settings = 'root.local'

    os.environ.setdefault("DJANGO_SETTINGS_MODULE", settings)

    try:
        from django.core.management import execute_from_command_line
    except ImportError as exc:
        raise ImportError(
            "Couldn't import Django. Are you sure it's installed and "
            "available on your PYTHONPATH environment variable? Did you "
            "forget to activate a virtual environment?"
        ) from exc
    execute_from_command_line(sys.argv)

