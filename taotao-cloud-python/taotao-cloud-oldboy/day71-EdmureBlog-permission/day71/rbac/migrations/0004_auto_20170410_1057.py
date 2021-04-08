# -*- coding: utf-8 -*-
# Generated by Django 1.10.6 on 2017-04-10 02:57
from __future__ import unicode_literals

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    dependencies = [
        ('rbac', '0003_auto_20170410_1006'),
    ]

    operations = [
        migrations.CreateModel(
            name='Menu',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('caption', models.CharField(max_length=32)),
                ('parent', models.ForeignKey(null=True, on_delete=django.db.models.deletion.CASCADE, related_name='p', to='rbac.Menu')),
            ],
        ),
        migrations.AlterModelOptions(
            name='permission2action',
            options={'verbose_name_plural': '权限表'},
        ),
        migrations.AlterModelOptions(
            name='permission2action2role',
            options={'verbose_name_plural': '角色分配权限'},
        ),
    ]
